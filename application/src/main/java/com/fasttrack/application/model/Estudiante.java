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
	public Estudiante(Long id_estudiante, String primer_nombre, String primer_apellido, String pais, String correo) {
		this.id = id_estudiante;
		this.primerNombre = primer_nombre;
		this.primerApellido = primer_apellido;
		this.pais = pais;
		this.correo = correo;
	}

	// Getters y Setters
	public Long getIdEstudiante() {
		return id;
	}

	public void setIdEstudiante(Long id_estudiante) {
		this.id = id_estudiante;
	}

	public String getPrimerNombre() {
		return primerNombre;
	}

	public void setPrimerNombre(String primer_nombre) {
		this.primerNombre = primer_nombre;
	}

	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(String primer_apellido) {
		this.primerApellido = primer_apellido;
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

	@Override
	public String toString() {
		return "Estudiante{" + "id estudiante=" + id + ", primer nombre='" + primerNombre + '\'' + ", primer apellido='"
				+ primerApellido + '\'' + ", pais='" + pais + '\'' + ", correo='" + correo + '\'' + '}';
	}
}
