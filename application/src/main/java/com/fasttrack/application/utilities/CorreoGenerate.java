package com.fasttrack.application.utilities;

public class CorreoGenerate {

	public static String generarCorreo(String primerNombre, String primerApellido) {
		return (primerNombre + "." + primerApellido).toLowerCase();
	}

}
