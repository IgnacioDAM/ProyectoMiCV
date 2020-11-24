package dad.javafx.proyecto.micv.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.javafx.proyecto.micv.model.Experiencia;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

public class ExperienciaController implements Initializable {

	// Model
	private ObjectProperty<Experiencia> experiencia = new SimpleObjectProperty<Experiencia>();

	// Vista
	@FXML
	private BorderPane view;

	@FXML
	private Button añadirExperiencia;

	@FXML
	private Button eliminarExperiencia;

	@FXML
	private TableView<?> experienciaTable;

	public ExperienciaController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ExperienciaView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.experiencia.addListener((o, ov, nv) -> onExperienciaChanged(o, ov, nv));
	}

	private void onExperienciaChanged(ObservableValue<? extends Experiencia> o, Experiencia ov, Experiencia nv) {
		if (ov != null) {

		}

		if (nv != null) {

		}
	}
	
	public BorderPane getView() {
		return view;
	}
	
	public final ObjectProperty<Experiencia> experienciaProperty() {
		return this.experiencia;
	}

	public final Experiencia getExperiencia() {
		return this.experienciaProperty().get();
	}

	public final void setExperiencia(final Experiencia experiencia) {
		this.experienciaProperty().set(experiencia);
	}

}
