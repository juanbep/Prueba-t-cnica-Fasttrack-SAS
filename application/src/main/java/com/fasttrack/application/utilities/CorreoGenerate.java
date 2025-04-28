package com.fasttrack.application.utilities;

public class CorreoGenerate {

	public static String generarCorreo(String primerNombre, String primerApellido, String dominio, String pais) {
		return (primerNombre + "." + primerApellido + "@" + dominio + "." + pais).toLowerCase();
	}
	
	public static String generarCorreo(String primerNombre, String primerApellido) {
		return (primerNombre + "." + primerApellido).toLowerCase();
	}

	public static String generarCorreo(String correo, int countDuplicateEmail) {
		int arrobaIndex = correo.indexOf('@');
		return correo.substring(0, arrobaIndex) + "." + (countDuplicateEmail + 1) + correo.substring(arrobaIndex);
	}
}
