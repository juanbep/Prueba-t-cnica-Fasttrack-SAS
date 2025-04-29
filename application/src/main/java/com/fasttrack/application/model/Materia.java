package com.fasttrack.application.model;

public class Materia {
	private Long id;
	private String nombre;
	private Long codigo;
	
	
	// Constructor por defecto
	public Materia() {
	}
	
	// Constructor parametrizado
	public Materia(Long id, String nombre, Long codigo) {
		this.id = id;
		this.nombre = nombre;
		this.codigo = codigo;
	}

	// Getters y Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	//toString
	@Override
	public String toString() {
		return "Materia [id=" + id + ", nombre=" + nombre + ", codigo=" + codigo + "]";
	}
	
}
