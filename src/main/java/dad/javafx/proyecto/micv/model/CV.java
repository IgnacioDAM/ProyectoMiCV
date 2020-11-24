package dad.javafx.proyecto.micv.model;

import org.hildan.fxgson.FxGson;

import com.google.gson.Gson;

import dad.javafx.proyecto.micv.model.Contacto.Contacto;
import dad.javafx.proyecto.micv.model.personal.Nacionalidad;
import dad.javafx.proyecto.micv.model.personal.Personal;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class CV {

	private ObjectProperty<Personal> personal = new SimpleObjectProperty<Personal>(new Personal());
	private ObjectProperty<Contacto> contacto = new SimpleObjectProperty<Contacto>(new Contacto());
	private ObjectProperty<Experiencia> experiencia = new SimpleObjectProperty<Experiencia>(new Experiencia());

	public static void main(String[] args) {

		CV cv = new CV();
		cv.getPersonal().setNombre("Chuck");
		cv.getPersonal().setApellidos("Norris");
		cv.getPersonal().getNacionalidades().add(new Nacionalidad("estadounidense"));

		Gson gson = FxGson.fullBuilder().setPrettyPrinting().create();

		String json = gson.toJson(cv);

		System.out.println(json);

	}

	public final ObjectProperty<Personal> personalProperty() {
		return this.personal;
	}

	public final Personal getPersonal() {
		return this.personalProperty().get();
	}

	public final void setPersonal(final Personal personal) {
		this.personalProperty().set(personal);
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
