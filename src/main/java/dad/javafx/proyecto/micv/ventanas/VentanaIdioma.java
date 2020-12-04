package dad.javafx.proyecto.micv.ventanas;

import dad.javafx.proyecto.micv.model.conocimiento.Idioma;
import dad.javafx.proyecto.micv.model.conocimiento.Nivel;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class VentanaIdioma extends Dialog<Idioma> {
	public VentanaIdioma() {
		setTitle("Nuevo idioma");

		Stage stage = (Stage) getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(this.getClass().getResource("/images/cv64x64.png").toString()));
		stage.setMinWidth(550);
		stage.setMinHeight(300);

		ButtonType crear = new ButtonType("Crear", ButtonData.OK_DONE);
		getDialogPane().getButtonTypes().addAll(crear, ButtonType.CANCEL);

		GridPane grid = new GridPane();
		grid.setHgap(5);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 10, 10, 10));

		TextField denominacion = new TextField();
		ComboBox<Nivel> nivel = new ComboBox<>();
		Button limpiarCombo = new Button("X");
		TextField certificacion = new TextField();
		HBox hBox = new HBox();

		hBox.getChildren().addAll(nivel, limpiarCombo);
		hBox.setSpacing(5);

		certificacion.setPrefWidth(160);

		nivel.getItems().addAll(Nivel.values());

		Node añadir = getDialogPane().lookupButton(crear);
		añadir.setDisable(true);

		añadir.disableProperty().bind(denominacion.textProperty().isEmpty().or(nivel.valueProperty().isNull())
				.or(certificacion.textProperty().isEmpty()));

		limpiarCombo.setOnAction(e -> {
			nivel.setValue(null);
		});

		grid.add(new Label("Denominación"), 0, 0);
		grid.add(denominacion, 1, 0);
		grid.add(new Label("Nivel"), 0, 1);
		grid.add(hBox, 1, 1);
		grid.add(new Label("Certificación"), 0, 2);

		GridPane.setColumnSpan(denominacion, 2);
		grid.add(certificacion, 1, 2);

		ColumnConstraints[] cols = { new ColumnConstraints(), new ColumnConstraints(), new ColumnConstraints() };

		cols[0].setHalignment(HPos.RIGHT);
		cols[2].setHgrow(Priority.ALWAYS);
		cols[2].setFillWidth(true);
		cols[2].setHalignment(HPos.LEFT);

		grid.getColumnConstraints().setAll(cols);

		getDialogPane().setContent(grid);

		Platform.runLater(() -> denominacion.requestFocus());

		setResultConverter(dialogButton -> {
			if (dialogButton == crear) {
				Idioma result = new Idioma();
				result.setDenominacion(denominacion.getText());
				result.setNivel(nivel.getValue());
				result.setCertificacion(certificacion.getText());
				return result;
			}
			return null;
		});
	}
}
