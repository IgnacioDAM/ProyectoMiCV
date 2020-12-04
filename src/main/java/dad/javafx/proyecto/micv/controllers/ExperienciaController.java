package dad.javafx.proyecto.micv.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.javafx.proyecto.micv.App;
import dad.javafx.proyecto.micv.model.Experiencia;
import dad.javafx.proyecto.micv.ventanas.VentanaExperiencia;
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

public class ExperienciaController implements Initializable {

	// Model
	private ListProperty<Experiencia> experiencia = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ObjectProperty<Experiencia> seleccionado = new SimpleObjectProperty<>();

	// Vista
	@FXML
	private BorderPane view;

	@FXML
	private Button añadirExperiencia;

	@FXML
	private Button eliminarExperiencia;

	@FXML
	private TableView<Experiencia> experienciaTable;

	@FXML
	private TableColumn<Experiencia, LocalDate> desde;

	@FXML
	private TableColumn<Experiencia, LocalDate> hasta;

	@FXML
	private TableColumn<Experiencia, String> denominacion;

	@FXML
	private TableColumn<Experiencia, String> empleador;

	public ExperienciaController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ExperienciaView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		desde.setCellValueFactory(v -> v.getValue().desdeProperty());
		hasta.setCellValueFactory(v -> v.getValue().hastaProperty());
		denominacion.setCellValueFactory(v -> v.getValue().denominacionProperty());
		empleador.setCellValueFactory(v -> v.getValue().empleadorProperty());

		desde.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
		hasta.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
		denominacion.setCellFactory(TextFieldTableCell.forTableColumn());
		empleador.setCellFactory(TextFieldTableCell.forTableColumn());

		this.experiencia.addListener((o, ov, nv) -> onExperienciaChanged(o, ov, nv));
	}

	private void onExperienciaChanged(ObservableValue<? extends ObservableList<Experiencia>> o,
			ObservableList<Experiencia> ov, ObservableList<Experiencia> nv) {
		if (ov != null) {
			experienciaTable.setItems(null);
			seleccionado.unbind();
			eliminarExperiencia.disableProperty().unbind();
		}

		if (nv != null) {
			experienciaTable.setItems(nv);
			seleccionado.bind(experienciaTable.getSelectionModel().selectedItemProperty());
			eliminarExperiencia.disableProperty().bind(Bindings.isEmpty(experienciaTable.getItems()));
		}
	}

	@FXML
	void onClickAñadir(ActionEvent event) {
		VentanaExperiencia ventana = new VentanaExperiencia();
		Optional<Experiencia> result = ventana.showAndWait();

		if (result.isPresent()) {
			experiencia.get().add(result.get());
		}
	}

	@FXML
	void onClickEliminar(ActionEvent event) {
		String title = "Eliminar formación";
		String header = "Confirmar eliminacion";
		String content = "¿Está seguro de borrar la experiencia?";
		Experiencia formacionSelec = seleccionado.get();

		if (experiencia != null && App.confirm(title, header, content)) {
			experiencia.get().remove(formacionSelec);
		}
	}

	public BorderPane getView() {
		return view;
	}

	public final ListProperty<Experiencia> experienciaProperty() {
		return this.experiencia;
	}

	public final ObservableList<Experiencia> getExperiencia() {
		return this.experienciaProperty().get();
	}

	public final void setExperiencia(final ObservableList<Experiencia> experiencia) {
		this.experienciaProperty().set(experiencia);
	}

}
