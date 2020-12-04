package dad.javafx.proyecto.micv.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.javafx.proyecto.micv.App;
import dad.javafx.proyecto.micv.model.Formacion;
import dad.javafx.proyecto.micv.ventanas.VentanaFormacion;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.util.converter.LocalDateStringConverter;

public class FormacionController implements Initializable {

	// model
	private ListProperty<Formacion> formacion = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ObjectProperty<Formacion> seleccionado = new SimpleObjectProperty<>();

	// view
	@FXML
	private BorderPane view;

	@FXML
	private Button añadirFormacion;

	@FXML
	private Button eliminarFormacion;

	@FXML
	private TableView<Formacion> formacionTable;

	@FXML
	private TableColumn<Formacion, LocalDate> desdeColumn;

	@FXML
	private TableColumn<Formacion, LocalDate> hastaColumn;

	@FXML
	private TableColumn<Formacion, String> denominacionColumn;

	@FXML
	private TableColumn<Formacion, String> organizadorColumn;

	public FormacionController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FormacionView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		desdeColumn.setCellValueFactory(v -> v.getValue().desdeProperty());
		hastaColumn.setCellValueFactory(v -> v.getValue().hastaProperty());
		denominacionColumn.setCellValueFactory(v -> v.getValue().denominacionProperty());
		organizadorColumn.setCellValueFactory(v -> v.getValue().organizadorProperty());

		desdeColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
		hastaColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
		denominacionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		organizadorColumn.setCellFactory(TextFieldTableCell.forTableColumn());

		this.formacion.addListener((o, ov, nv) -> onTituloChanged(o, ov, nv));
	}

	private void onTituloChanged(ObservableValue<? extends ObservableList<Formacion>> o, ObservableList<Formacion> ov,
			ObservableList<Formacion> nv) {
		if (ov != null) {
			formacionTable.setItems(null);
			seleccionado.unbind();
			eliminarFormacion.disableProperty().unbind();
		}

		if (nv != null) {
			formacionTable.setItems(nv);
			seleccionado.bind(formacionTable.getSelectionModel().selectedItemProperty());
			eliminarFormacion.disableProperty().bind(Bindings.isEmpty(formacionTable.getItems()));
		}

	}

	@FXML
	void onClickAñadir(ActionEvent event) {
		VentanaFormacion ventana = new VentanaFormacion();
		Optional<Formacion> result = ventana.showAndWait();

		if (result.isPresent()) {
			formacion.get().add(result.get());
		}		
	}

	@FXML
	void onClickEliminar(ActionEvent event) {
		String title = "Eliminar formación";
		String header = "Confirmar eliminacion";
		String content = "¿Está seguro de borrar la formación?";
		Formacion formacionSelec = seleccionado.get();

		if (formacion != null && App.confirm(title, header, content)) {
			formacion.get().remove(formacionSelec);
		}
	}

	public BorderPane getView() {
		return view;
	}

	public final ListProperty<Formacion> formacionProperty() {
		return this.formacion;
	}

	public final ObservableList<Formacion> getFormacion() {
		return this.formacionProperty().get();
	}

	public final void setFormacion(final ObservableList<Formacion> titulo) {
		this.formacionProperty().set(titulo);
	}

}
