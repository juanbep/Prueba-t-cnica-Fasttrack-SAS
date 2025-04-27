package com.fasttrack.application.model;


// Revisar problema con lombok
// import lombok.*;
// @Data @AllArgsConstructor @NoArgsConstructor @Getter @Setter

public class Estudiante {
	private Long id_estudiante; 
	private String primer_nombre;
	private String primer_apellido;
	private String pais;
	private String correo;
	
	
	// Constructor por defecto 
    public Estudiante() {}

    // Constructor parametrizado
    public Estudiante(Long id_estudiante, String primer_nombre, String primer_apellido, String pais, String correo) {
        this.id_estudiante = id_estudiante;
        this.primer_nombre = primer_nombre;
        this.primer_apellido = primer_apellido;
        this.pais = pais;
        this.correo = correo;
    }

    // Getters y Setters
    public Long getIdEstudiante() {
        return id_estudiante;
    }

    public void setIdEstudiante(Long id_estudiante) {
        this.id_estudiante = id_estudiante;
    }

    public String getPrimerNombre() {
        return primer_nombre;
    }

    public void setPrimerNombre(String primer_nombre) {
        this.primer_nombre = primer_nombre;
    }

    public String getPrimerApellido() {
        return primer_apellido;
    }

    public void setPrimerApellido(String primer_apellido) {
        this.primer_apellido = primer_apellido;
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
        return "Estudiante{" +
                "id_estudiante=" + id_estudiante +
                ", primer_nombre='" + primer_nombre + '\'' +
                ", primer_apellido='" + primer_apellido + '\'' +
                ", pais='" + pais + '\'' +
                ", correo='" + correo + '\'' +
                '}';
    }
}
