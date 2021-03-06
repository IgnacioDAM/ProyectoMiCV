package dad.javafx.proyecto.micv;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.google.gson.JsonSyntaxException;

import dad.javafx.proyecto.micv.controllers.ConocimientoController;
import dad.javafx.proyecto.micv.controllers.ContactoController;
import dad.javafx.proyecto.micv.controllers.ExperienciaController;
import dad.javafx.proyecto.micv.controllers.PersonalController;
import dad.javafx.proyecto.micv.controllers.FormacionController;
import dad.javafx.proyecto.micv.model.CV;
import dad.javafx.proyecto.micv.utils.JSONUtils;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class MainController implements Initializable {

	// controllers

	private PersonalController personalController = new PersonalController();
	private ContactoController contactoController = new ContactoController();
	private ExperienciaController experienciaController = new ExperienciaController();
	private FormacionController tituloController = new FormacionController();
	private ConocimientoController conocimientoController = new ConocimientoController();

	// model

	private ObjectProperty<CV> cv = new SimpleObjectProperty<>();
	private File ficheroActual;

	// view

	@FXML
	private BorderPane view;

	@FXML
	private Tab personalTab;

	@FXML
	private Tab contactoTab;

	@FXML
	private Tab formacionTab;

	@FXML
	private Tab experienciaTab;

	@FXML
	private Tab conocimientosTab;

	public MainController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		personalTab.setContent(personalController.getView());
		contactoTab.setContent(contactoController.getView());
		experienciaTab.setContent(experienciaController.getView());
		conocimientosTab.setContent(conocimientoController.getView());
		formacionTab.setContent(tituloController.getView());

		cv.addListener((o, ov, nv) -> onCVChanged(o, ov, nv));

		cv.set(new CV());
		
		ficheroActual = null;
	}

	private void onCVChanged(ObservableValue<? extends CV> o, CV ov, CV nv) {

		if (ov != null) {
			personalController.personalProperty().unbind();
			contactoController.contactoProperty().unbind();
			experienciaController.experienciaProperty().unbind();
			conocimientoController.conocimientoProperty().unbind();
			tituloController.formacionProperty().unbind();
		}

		if (nv != null) {
			personalController.personalProperty().bind(nv.personalProperty());
			contactoController.contactoProperty().bind(nv.contactoProperty());
			experienciaController.experienciaProperty().bind(nv.experienciaProperty());
			conocimientoController.conocimientoProperty().bind(nv.conocimientoProperty());
			tituloController.formacionProperty().bind(nv.tituloProperty());
		}

	}

	public BorderPane getView() {
		return view;
	}

	@FXML
	void onAbrirAction(ActionEvent event) {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Abrir un currículum");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Currículum (*.cv)", "*.cv"));
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Todos los archivos (*.*)", "*.*"));
		File cvFile = fileChooser.showOpenDialog(App.getPrimaryStage());
		if (cvFile != null) {

			try {
				cv.set(JSONUtils.fromJson(cvFile, CV.class));
				App.info("Se ha abierto el fichero " + cvFile.getName() + " correctamente.", "Abierto");
			} catch (JsonSyntaxException | IOException e) {
				App.error("Ha ocurrido un error al abrir " + cvFile, e.getMessage());
			}

		}

	}

	@FXML
	void onAcercaDeAction(ActionEvent event) {
		// Sin funcion de momento
	}

	@FXML
	void onCerrarAction(ActionEvent event) {
		Platform.exit();
	}

	@FXML
	void onGuardarAction(ActionEvent event) {
		if (ficheroActual == null) {
			onGuardarComoAction(event);
		} else {
			try {
				JSONUtils.toJson(ficheroActual, cv.get());
			} catch (JsonSyntaxException | IOException e) {
				App.error("Ha ocurrido un error al guardar " + ficheroActual, e.getMessage());
			}
		}
	}

	@FXML
	void onGuardarComoAction(ActionEvent event) {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Guardar un currículum");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Currículum (*.cv)", "*.cv"));
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Todos los archivos (*.*)", "*.*"));
		File cvFile = fileChooser.showSaveDialog(App.getPrimaryStage());
		if (cvFile != null) {

			try {
				JSONUtils.toJson(cvFile, cv.get());
			} catch (JsonSyntaxException | IOException e) {
				App.error("Ha ocurrido un error al guardar " + cvFile, e.getMessage());
			}

		}

	}

	@FXML
	void onNuevoAction(ActionEvent event) {
		System.out.println("Nuevo");
		cv.set(new CV());
	}

}
