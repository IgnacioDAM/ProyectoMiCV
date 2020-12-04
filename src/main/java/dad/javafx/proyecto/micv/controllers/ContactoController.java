package dad.javafx.proyecto.micv.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.javafx.proyecto.micv.App;
import dad.javafx.proyecto.micv.model.contacto.Contacto;
import dad.javafx.proyecto.micv.model.contacto.Email;
import dad.javafx.proyecto.micv.model.contacto.Telefono;
import dad.javafx.proyecto.micv.model.contacto.TipoTelefono;
import dad.javafx.proyecto.micv.model.contacto.Web;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

public class ContactoController implements Initializable {

	// model
	private ObjectProperty<Contacto> contacto = new SimpleObjectProperty<>();
	private ObjectProperty<Telefono> telefonoSeleccionado = new SimpleObjectProperty<>();
	private ObjectProperty<Email> emailSeleccionado = new SimpleObjectProperty<>();
	private ObjectProperty<Web> urlSeleccionado = new SimpleObjectProperty<>();

	@FXML
	private SplitPane view;

	@FXML
	private Button añadirTelefono;

	@FXML
	private Button eliminarTelefono;

	@FXML
	private TableView<Telefono> telefonosTable;

	@FXML
	private TableColumn<Telefono, String> numeroColumn;

	@FXML
	private TableColumn<Telefono, TipoTelefono> tipoColumn;

	@FXML
	private Button añadirEmail;

	@FXML
	private Button eliminarEmail;

	@FXML
	private TableView<Email> correoTable;

	@FXML
	private TableColumn<Email, String> emailColumn;

	@FXML
	private Button añadirUrl;

	@FXML
	private Button eliminarUrl;

	@FXML
	private TableView<Web> urlTable;

	@FXML
	private TableColumn<Web, String> urlColumn;

	public ContactoController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ContactoView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// tabla teléfonos
		numeroColumn.setCellValueFactory(v -> v.getValue().numeroProperty());
		tipoColumn.setCellValueFactory(v -> v.getValue().tipoTelefonoProperty());

		numeroColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		tipoColumn.setCellFactory(ComboBoxTableCell.forTableColumn(TipoTelefono.values()));

		// tabla email
		emailColumn.setCellValueFactory(v -> v.getValue().direccionProperty());
		emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());

		// tabla web
		urlColumn.setCellValueFactory(v -> v.getValue().urlProperty());
		urlColumn.setCellFactory(TextFieldTableCell.forTableColumn());

		this.contacto.addListener((o, ov, nv) -> onContactoChanged(o, ov, nv));
	}

	private void onContactoChanged(ObservableValue<? extends Contacto> o, Contacto ov, Contacto nv) {
		if (ov != null) {
			telefonosTable.itemsProperty().unbind();
			telefonoSeleccionado.unbind();
			correoTable.itemsProperty().unbind();
			emailSeleccionado.unbind();
			urlTable.itemsProperty().unbind();
			urlSeleccionado.unbind();
			eliminarTelefono.disableProperty().unbind();
			eliminarEmail.disableProperty().unbind();
			eliminarUrl.disableProperty().unbind();
		}

		if (nv != null) {
			telefonosTable.itemsProperty().bind(nv.telefonosProperty());
			telefonoSeleccionado.bind(telefonosTable.getSelectionModel().selectedItemProperty());
			correoTable.itemsProperty().bind(nv.emailProperty());
			emailSeleccionado.bind(correoTable.getSelectionModel().selectedItemProperty());
			urlTable.itemsProperty().bind(nv.webProperty());
			urlSeleccionado.bind(urlTable.getSelectionModel().selectedItemProperty());
			eliminarTelefono.disableProperty().bind(Bindings.isEmpty(telefonosTable.getItems()));
			eliminarEmail.disableProperty().bind(Bindings.isEmpty(correoTable.getItems()));
			eliminarUrl.disableProperty().bind(Bindings.isEmpty(urlTable.getItems()));
		}

	}

	@FXML
	void onClickAñadirTelefono(ActionEvent event) {
		Dialog<Pair<String, TipoTelefono>> dialog = new Dialog<>();

		dialog.setTitle("Nuevo teléfono");
		dialog.setHeaderText("Introduce el nuevo numero de telefono");

		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(this.getClass().getResource("/images/cv64x64.png").toString()));

		// botones
		ButtonType añadirButtonType = new ButtonType("Añadir", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(añadirButtonType, ButtonType.CANCEL);

		// contenido
		GridPane contenedor = new GridPane();
		contenedor.setHgap(10);
		contenedor.setVgap(10);
		contenedor.setPadding(new Insets(20, 150, 10, 10));

		TextField numero = new TextField();
		numero.setPromptText("Número de teléfono");

		ComboBox<TipoTelefono> tipoTelefono = new ComboBox<>();
		tipoTelefono.getItems().addAll(TipoTelefono.values());
		tipoTelefono.setPromptText("Seleccione un tipo");

		contenedor.add(new Label("Número: "), 0, 0);
		contenedor.add(numero, 1, 0);
		contenedor.add(new Label("Tipo: "), 0, 1);
		contenedor.add(tipoTelefono, 1, 1);

		dialog.getDialogPane().setContent(contenedor);

		Platform.runLater(() -> tipoTelefono.requestFocus());

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == añadirButtonType) {
				return new Pair<>(numero.getText(), tipoTelefono.getSelectionModel().getSelectedItem());
			}
			return null;
		});

		Optional<Pair<String, TipoTelefono>> resultado = dialog.showAndWait();

		if (resultado.isPresent()) {
			Telefono tlf = new Telefono();
			tlf.setNumero(resultado.get().getKey());
			tlf.setTipoTelefono(resultado.get().getValue());
			contacto.get().getTelefonos().add(tlf);
		}
	}

	@FXML
	void onClickAñadirCorreo(ActionEvent event) {
		TextInputDialog dialogo = new TextInputDialog();
		dialogo.setTitle("Nuevo e-mail");
		dialogo.setHeaderText("Crear una nueva dirección de correo");
		dialogo.setContentText("E-mail: ");

		Stage stage = (Stage) dialogo.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(this.getClass().getResource("/images/cv64x64.png").toString()));

		// camino para tener un valor responive.
		Optional<String> result = dialogo.showAndWait();
		if (result.isPresent()) {
			Email email = new Email();
			email.setDireccion(result.get());
			contacto.get().emailProperty().add(email);
		}
	}

	@FXML
	void onClickAñadirUrl(ActionEvent event) {
		TextInputDialog dialogo = new TextInputDialog();
		dialogo.setTitle("Nueva web");
		dialogo.setHeaderText("Crear una nueva dirección web");
		dialogo.setContentText("URL: ");
		dialogo.getEditor().setText("http://");
		Platform.runLater(() -> dialogo.getEditor().requestFocus());

		Stage stage = (Stage) dialogo.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(this.getClass().getResource("/images/cv64x64.png").toString()));

		// camino para tener un valor responive.
		Optional<String> result = dialogo.showAndWait();
		if (result.isPresent()) {
			Web web = new Web();
			web.setUrl(result.get());
			contacto.get().webProperty().add(web);
		}
	}

	@FXML
	void onClickEliminarCorreo(ActionEvent event) {
		String title = "Eliminar e-mail";
		String header = "Confirmar eliminacion";
		String content = "¿Está seguro de borrar el e-mail?";
		Email email = emailSeleccionado.get();

		if (email != null && App.confirm(title, header, content))
			contacto.get().getEmail().remove(email);
	}

	@FXML
	void onClickEliminarTelefono(ActionEvent event) {
		String title = "Eliminar teléfono";
		String header = "Confirmar eliminacion";
		String content = "¿Está seguro de borrar el teléfono?";
		Telefono telefono = telefonoSeleccionado.get();

		if (telefono != null && App.confirm(title, header, content))
			contacto.get().getTelefonos().remove(telefono);
	}

	@FXML
	void onClickEliminarUrl(ActionEvent event) {
		String title = "Eliminar web";
		String header = "Confirmar eliminacion";
		String content = "¿Está seguro de borrar la dirección web?";
		Web web = urlSeleccionado.get();

		if (web != null && App.confirm(title, header, content))
			contacto.get().getWeb().remove(web);
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

	public ObjectProperty<Telefono> getTelefonoSeleccionado() {
		return telefonoSeleccionado;
	}

	public void setTelefonoSeleccionado(ObjectProperty<Telefono> telefonoSeleccionado) {
		this.telefonoSeleccionado = telefonoSeleccionado;
	}

	public ObjectProperty<Email> getEmailSeleccionado() {
		return emailSeleccionado;
	}

	public void setEmailSeleccionado(ObjectProperty<Email> emailSeleccionado) {
		this.emailSeleccionado = emailSeleccionado;
	}

	public ObjectProperty<Web> getUrlSeleccionado() {
		return urlSeleccionado;
	}

	public void setUrlSeleccionado(ObjectProperty<Web> urlSeleccionado) {
		this.urlSeleccionado = urlSeleccionado;
	}

}
