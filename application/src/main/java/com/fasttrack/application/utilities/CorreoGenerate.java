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
	
	/*
	 * public static String actualizarCorreo(String primerNombre, String
	 * primerApellido, String correo) { String[] splitCorreo = correo.split("@");
	 * String usuario = splitCorreo[0]; String dominio = splitCorreo[1];
	 * 
	 * String[] splitUsuario = usuario.split("\\.");
	 * 
	 * String nuevoUsuario; if (splitUsuario.length == 3) { nuevoUsuario =
	 * primerNombre + "." + primerApellido + "." + splitUsuario[2]; } else {
	 * nuevoUsuario = primerNombre + "." + primerApellido; }
	 * 
	 * return nuevoUsuario + "@" + dominio;
	 * 
	 * }
	 */
}
