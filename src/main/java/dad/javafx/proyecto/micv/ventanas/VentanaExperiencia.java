package dad.javafx.proyecto.micv.ventanas;

import dad.javafx.proyecto.micv.model.Experiencia;

public class VentanaExperiencia extends VentanaConFechas<Experiencia> {

	public VentanaExperiencia() {
		super("Nuevo título", "Denominación", "Empleador", "Desde", "Hasta");

		setResultConverter(dialogButton -> {
			if (dialogButton == crearButton) {
				Experiencia resultado = new Experiencia();
				resultado.setDenominacion(primerText.getText());
				resultado.setEmpleador(segundoText.getText());
				resultado.setDesde(desde.getValue());
				resultado.setHasta(hasta.getValue());
				return resultado;
			}
			return null;
		});
	}

}
