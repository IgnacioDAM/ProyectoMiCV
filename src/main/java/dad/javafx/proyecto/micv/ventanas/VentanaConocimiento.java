package dad.javafx.proyecto.micv.ventanas;

import dad.javafx.proyecto.micv.model.conocimiento.Conocimiento;
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
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class VentanaConocimiento extends Dialog<Conocimiento> {
	public VentanaConocimiento() {
		setTitle("Nuevo conocimiento");

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

		nivel.getItems().addAll(Nivel.values());

		Node añadir = getDialogPane().lookupButton(crear);
		añadir.setDisable(true);

		añadir.disableProperty()
				.bind(denominacion.textProperty().isEmpty().or(nivel.valueProperty().isNull()));

		limpiarCombo.setOnAction(e -> {
			nivel.setValue(null);
		});

		grid.add(new Label("Denominación"), 0, 0);
		grid.add(denominacion, 1, 0);
		grid.add(new Label("Nivel"), 0, 1);
		grid.add(nivel, 1, 1);
		grid.add(limpiarCombo, 2, 1);

		GridPane.setColumnSpan(denominacion, 2);

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
				Conocimiento result = new Conocimiento();
				result.setDenominacion(denominacion.getText());
				result.setNivel(nivel.getValue());
				return result;
			}
			return null;
		});
	}
}
