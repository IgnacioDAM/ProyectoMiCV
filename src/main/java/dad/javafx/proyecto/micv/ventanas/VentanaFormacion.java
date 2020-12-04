package dad.javafx.proyecto.micv.ventanas;

import dad.javafx.proyecto.micv.model.Formacion;

public class VentanaFormacion extends VentanaConFechas<Formacion> {

	public VentanaFormacion() {
		super("Nuevo título", "Denominación", "Organizador", "Desde", "Hasta");

		setResultConverter(dialogButton -> {
			if (dialogButton == crearButton) {
				Formacion resultado = new Formacion();
				resultado.setDenominacion(primerText.getText());
				resultado.setOrganizador(segundoText.getText());
				resultado.setDesde(desde.getValue());
				resultado.setHasta(hasta.getValue());
				return resultado;
			}
			return null;
		});
	}
}
