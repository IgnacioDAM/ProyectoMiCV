package dad.javafx.proyecto.micv.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.javafx.proyecto.micv.model.Contacto.Contacto;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableView;

public class ContactoController implements Initializable {

	// model
	private ObjectProperty<Contacto> contacto = new SimpleObjectProperty<>();

	@FXML
    private SplitPane view;
	
	@FXML
	private Button añadirTelefono;

	@FXML
	private Button eliminarTelefono;

	@FXML
	private TableView<?> telefonosTable;

	@FXML
	private Button añadirEmail;

	@FXML
	private Button eliminarEmail;

	@FXML
	private TableView<?> correoTable;

	@FXML
	private Button añadirUrl;

	@FXML
	private Button eliminarUrl;

	@FXML
	private TableView<?> urlTable;

	public ContactoController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ContactoView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.contacto.addListener((o, ov, nv) -> onContactoChanged(o, ov, nv));
	}

	private void onContactoChanged(ObservableValue<? extends Contacto> o, Contacto ov, Contacto nv) {
		if (ov != null) {

		}
	}
	
	public SplitPane getView() {
		return view;
	}

	public final ObjectProperty<Contacto> contactoProperty() {
		return this.contacto;
	}

	public final Contacto getContacto() {
		return this.contactoProperty().get();
	}

	public final void setContacto(final Contacto contacto) {
		this.contactoProperty().set(contacto);
	}

}
