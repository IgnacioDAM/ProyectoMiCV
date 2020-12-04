package dad.javafx.proyecto.micv.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.javafx.proyecto.micv.App;
import dad.javafx.proyecto.micv.model.conocimiento.Conocimiento;
import dad.javafx.proyecto.micv.model.conocimiento.Idioma;
import dad.javafx.proyecto.micv.model.conocimiento.Nivel;
import dad.javafx.proyecto.micv.ventanas.VentanaConocimiento;
import dad.javafx.proyecto.micv.ventanas.VentanaIdioma;
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
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;

public class ConocimientoController implements Initializable {

	// model
	private ListProperty<Conocimiento> conocimiento = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ObjectProperty<Conocimiento> seleccionado = new SimpleObjectProperty<>();

	// view
	@FXML
	private BorderPane view;

	@FXML
	private Button añadirConocimiento;

	@FXML
	private Button añadirIdioma;

	@FXML
	private Button eliminarConocimiento;

	@FXML
	private TableView<Conocimiento> conocimientoTable;

	@FXML
	private TableColumn<Conocimiento, String> denominacion;

	@FXML
	private TableColumn<Conocimiento, Nivel> nivel;

	public ConocimientoController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ConocimientosView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		denominacion.setCellValueFactory(v -> v.getValue().denominacionProperty());
		nivel.setCellValueFactory(v -> v.getValue().nivelProperty());

		denominacion.setCellFactory(TextFieldTableCell.forTableColumn());
		nivel.setCellFactory(ComboBoxTableCell.forTableColumn(Nivel.values()));

		this.conocimiento.addListener((o, ov, nv) -> onConocimientoChanged(o, ov, nv));
	}

	private void onConocimientoChanged(ObservableValue<? extends ObservableList<Conocimiento>> o,
			ObservableList<Conocimiento> ov, ObservableList<Conocimiento> nv) {
		if (ov != null) {
			conocimientoTable.setItems(null);
			seleccionado.unbind();
			eliminarConocimiento.disableProperty().unbind();
		}

		if (nv != null) {
			conocimientoTable.setItems(nv);
			seleccionado.bind(conocimientoTable.getSelectionModel().selectedItemProperty());
			eliminarConocimiento.disableProperty().bind(Bindings.isEmpty(conocimientoTable.getItems()));
		}
	}

	@FXML
	void onClickAñadirConocimiento(ActionEvent event) {
		VentanaConocimiento ventana = new VentanaConocimiento();
		Optional<Conocimiento> result = ventana.showAndWait();

		if (result.isPresent()) {
			conocimiento.get().add(result.get());
		}
	}

	@FXML
	void onClickAñadirIdioma(ActionEvent event) {
		VentanaIdioma ventana = new VentanaIdioma();
		Optional<Idioma> result = ventana.showAndWait();

		if (result.isPresent()) {
			conocimiento.get().add(result.get());
		}
	}

	@FXML
	void onClickEliminar(ActionEvent event) {
		String title = "Eliminar formación";
		String header = "Confirmar eliminacion";
		String content = "¿Está seguro de borrar el conocimiento?";
		Conocimiento conocimientoSelec = seleccionado.get();

		if (conocimiento != null && App.confirm(title, header, content)) {
			conocimiento.get().remove(conocimientoSelec);
		}
	}

	public BorderPane getView() {
		return this.view;
	}

	public final ListProperty<Conocimiento> conocimientoProperty() {
		return this.conocimiento;
	}

	public final ObservableList<Conocimiento> getConocimiento() {
		return this.conocimientoProperty().get();
	}

	public final void setConocimiento(final ObservableList<Conocimiento> conocimiento) {
		this.conocimientoProperty().set(conocimiento);
	}

}
