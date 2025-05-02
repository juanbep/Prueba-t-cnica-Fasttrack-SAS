package com.fasttrack.application.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasttrack.application.model.Estudiante;
import com.fasttrack.application.repository.EstudianteRepository;
import com.fasttrack.application.utilities.CorreoGenerate;

@Service
public class EstudianteService {

	private final EstudianteRepository estudianteRepository;

	public EstudianteService(EstudianteRepository estudianteRepository) {
		this.estudianteRepository = estudianteRepository;
	}

	public void registrarEstudiante(Estudiante estudiante) throws Exception {
		// validaciones

		//System.out.println(estudiante.getPais());
		estudiante.setCorreo(
				asignarCorreo(estudiante.getPrimerNombre(), estudiante.getPrimerApellido(), estudiante.getPais()));

		if (!estudianteRepository.save(estudiante)) {
			throw new RuntimeException("Error al actualizar en BD");
		}
	}

	public void actualizarEstudiante(Long id, Estudiante estudianteUpdate) throws Exception {
		// Validar si existe
		if (!estudianteRepository.existsById(id)) {
			throw new IllegalArgumentException("Estudiante no encontrado");
		}

		estudianteUpdate.setId(id);

		estudianteUpdate.setCorreo(asignarCorreo(estudianteUpdate.getPrimerNombre(),
				estudianteUpdate.getPrimerApellido(),estudianteUpdate.getPais()));

		if (!estudianteRepository.update(estudianteUpdate)) {
			throw new RuntimeException("Error al actualizar en BD");
		}
	}

	public List<Estudiante> listarEstudiantes() throws Exception {
		// validaciones
		return estudianteRepository.findAll();
	}

	public void eliminarEstudiante(Long id) throws Exception {

		if (!estudianteRepository.existsById(id)) {
			throw new IllegalArgumentException("No se encontró el estudiante con ID: " + id);
		}

		// validaciones
		// Validar que no tenga materias asignadas

		boolean delete = estudianteRepository.deleteById(id);
		if (!delete) {
			throw new RuntimeException("No se pudo eliminar el estudiante con ID: " + id);
		}
	}

	private String asignarCorreo(String primerNombre, String primerApellido, String pais) throws SQLException {

		String correo = CorreoGenerate.generarCorreo(primerNombre, primerApellido, "fasttrack.com", pais);
		String correoAux = CorreoGenerate.generarCorreo(primerNombre, primerApellido);

		int countDuplicateEmail = estudianteRepository.duplicateEmail(correoAux);

		if (countDuplicateEmail == 0) {
			return correo;
		} else {
			String modifiedEmail = CorreoGenerate.generarCorreo(correo, countDuplicateEmail);
			return modifiedEmail;
		}
	}
}