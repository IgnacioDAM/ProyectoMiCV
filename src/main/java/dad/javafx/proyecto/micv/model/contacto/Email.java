package dad.javafx.proyecto.micv.model.contacto;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Email {
	private StringProperty direccion = new SimpleStringProperty();

	public Email() {

	}

	public Email(String direccion) {
		setDireccion(direccion);
	}

	public final StringProperty direccionProperty() {
		return this.direccion;
	}

	public final String getDireccion() {
		return this.direccionProperty().get();
	}

	public final void setDireccion(final String direccion) {
		this.direccionProperty().set(direccion);
	}

}
