package dad.javafx.proyecto.micv.ventanas;

import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class VentanaConFechas<T> extends Dialog<T> {
	
	protected ButtonType crearButton;
	protected TextField primerText;
	protected TextField segundoText;
	protected DatePicker desde;
	protected DatePicker hasta;

	public VentanaConFechas(String titulo, String label1, String label2, String label3, String label4) {
		setTitle(titulo);
		
		Stage stage = (Stage) getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(this.getClass().getResource("/images/cv64x64.png").toString()));
		stage.setMinWidth(550);
		stage.setMinHeight(200);
		
		crearButton = new ButtonType("Crear", ButtonData.OK_DONE);
		getDialogPane().getButtonTypes().addAll(crearButton, ButtonType.CANCEL);
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 10, 10, 10));
		
		primerText = new TextField();
		segundoText = new TextField();
		desde = new DatePicker();
		hasta = new DatePicker();
		
		Node añadirNode = getDialogPane().lookupButton(crearButton);
		añadirNode.setDisable(true);
		
		añadirNode.disableProperty().bind(
				primerText.textProperty().isEmpty().or(
				segundoText.textProperty().isEmpty()).or(
				desde.valueProperty().isNull()).or(
				hasta.valueProperty().isNull()));
		
		grid.add(new Label(label1), 0, 0);
		grid.add(primerText, 1, 0);
		grid.add(new Label(label2), 0, 1);
		grid.add(segundoText, 1, 1);
		grid.add(new Label(label3), 0, 2);
		grid.add(desde, 1, 2);
		grid.add(new Label(label4), 0, 3);
		grid.add(hasta, 1, 3);
		
		GridPane.setColumnSpan(primerText, 2);
		GridPane.setColumnSpan(segundoText, 2);
		
		ColumnConstraints[] cols = {
				new ColumnConstraints(),
				new ColumnConstraints(),
				new ColumnConstraints()
		};
		
		cols[0].setHalignment(HPos.RIGHT);
		cols[1].setHgrow(Priority.ALWAYS);
		cols[1].setFillWidth(true);
		
		grid.getColumnConstraints().setAll(cols);
		
		getDialogPane().setContent(grid);
		
		Platform.runLater(() -> primerText.requestFocus());
	}

}
