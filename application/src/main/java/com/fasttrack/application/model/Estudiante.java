package com.fasttrack.application.model;

// Revisar problema con lombok
// import lombok.*;
// @Data @AllArgsConstructor @NoArgsConstructor @Getter @Setter

public class Estudiante {
	private Long id;
	private String primerNombre;
	private String primerApellido;
	private String pais;
	private String correo;

	// Constructor por defecto
	public Estudiante() {
	}

	// Constructor parametrizado
	public Estudiante(Long id, String primerNombre, String primerApellido, String pais, String correo) {
		this.id = id;
		this.primerNombre = primerNombre;
		this.primerApellido = primerApellido;
		this.pais = pais;
		this.correo = correo;
	}

	// Getters y Setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPrimerNombre() {
		return primerNombre;
	}

	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}

	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	//toString
	@Override
	public String toString() {
		return "Estudiante [id=" + id + ", primerNombre=" + primerNombre + ", primerApellido=" + primerApellido
				+ ", pais=" + pais + ", correo=" + correo + "]";
	}
	
}
