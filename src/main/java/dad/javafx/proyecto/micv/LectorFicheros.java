package dad.javafx.proyecto.micv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public abstract class LectorFicheros {
	
	public static ArrayList<String> leerFichero(String nombre) throws IOException {
		ArrayList<String> resultado = new ArrayList<String>();
		FileReader fichero = new FileReader(LectorFicheros.class.getResource(nombre).getFile());
		BufferedReader buffered = new BufferedReader(fichero);
		String linea;

		while ((linea = buffered.readLine()) != null) {
			resultado.add(linea);
		}

		buffered.close();
		fichero.close();

		return resultado;
	}
}
