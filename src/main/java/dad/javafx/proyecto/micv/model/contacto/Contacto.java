package dad.javafx.proyecto.micv.model.contacto;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Contacto {
	private ListProperty<Telefono> telefonos = new SimpleListProperty<Telefono>(FXCollections.observableArrayList());
	private ListProperty<Email> email = new SimpleListProperty<Email>(FXCollections.observableArrayList());
	private ListProperty<Web> web = new SimpleListProperty<Web>(FXCollections.observableArrayList());

	public final ListProperty<Telefono> telefonosProperty() {
		return this.telefonos;
	}

	public final ObservableList<Telefono> getTelefonos() {
		return this.telefonosProperty().get();
	}

	public final void setTelefonos(final ObservableList<Telefono> telefonos) {
		this.telefonosProperty().set(telefonos);
	}

	public final ListProperty<Email> emailProperty() {
		return this.email;
	}

	public final ObservableList<Email> getEmail() {
		return this.emailProperty().get();
	}

	public final void setEmail(final ObservableList<Email> email) {
		this.emailProperty().set(email);
	}

	public final ListProperty<Web> webProperty() {
		return this.web;
	}

	public final ObservableList<Web> getWeb() {
		return this.webProperty().get();
	}

	public final void setWeb(final ObservableList<Web> web) {
		this.webProperty().set(web);
	}

}
