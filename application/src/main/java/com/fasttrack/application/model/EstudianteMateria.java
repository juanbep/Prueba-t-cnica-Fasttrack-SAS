package com.fasttrack.application.model;

public class EstudianteMateria {
	private Long id;
	private Long idEstudiante;
	private Long idMateria;

	// Constructor por defecto
	public EstudianteMateria() {
	}

	// Constructor parametrizado
	public EstudianteMateria(Long id, Long idEstudiante, Long idMateria) {
		super();
		this.id = id;
		this.idEstudiante = idEstudiante;
		this.idMateria = idMateria;
	}

	// Getters y Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdEstudiante() {
		return idEstudiante;
	}

	public void setIdEstudiante(Long idEstudiante) {
		this.idEstudiante = idEstudiante;
	}

	public Long getIdMateria() {
		return idMateria;
	}

	public void setIdMateria(Long idMateria) {
		this.idMateria = idMateria;
	}

	//toString
	@Override
	public String toString() {
		return "EstudianteMateria [id=" + id + ", idEstudiante=" + idEstudiante + ", idMateria=" + idMateria + "]";
	}
	
	
}
