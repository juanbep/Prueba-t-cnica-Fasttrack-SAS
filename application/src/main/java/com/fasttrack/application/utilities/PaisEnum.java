package com.fasttrack.application.utilities;

public enum PaisEnum {
	COLOMBIA("CO"), USA("US"), MEXICO("MX"), ARGENTINA("AR"), CHILE("CL"), BRASIL("BR"), PERU("PE"), ECUADOR("EC"),
	ESPAÑA("ES"), FRANCIA("FR");

	private final String codigoISO;

	PaisEnum(String codigoISO) {
		this.codigoISO = codigoISO;
	}

	public String getCodigoISO() {
		return codigoISO;
	}

	public static String obtenerCodigoISO(String nombrePais) {
		for (PaisEnum pais : values()) {
			return pais.getCodigoISO();
		}
		throw new IllegalArgumentException("País no soportado: " + nombrePais);
	}
}
