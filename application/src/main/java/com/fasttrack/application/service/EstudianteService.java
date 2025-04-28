package com.fasttrack.application.service;

import com.fasttrack.application.model.Estudiante;
import com.fasttrack.application.repository.EstudianteRepository;
import com.fasttrack.application.utilities.CorreoGenerate;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class EstudianteService {

	private final EstudianteRepository estudianteRepository;

	public EstudianteService(EstudianteRepository estudianteRepository) {
		this.estudianteRepository = estudianteRepository;
	}

	public void registrarEstudiante(Estudiante estudiante) throws Exception {
		// validaciones

		String correo = CorreoGenerate.generarCorreo(estudiante.getPrimerNombre(), estudiante.getPrimerApellido(),
				"fasttrack.com", estudiante.getPais());

		String correoAux = CorreoGenerate.generarCorreo(estudiante.getPrimerNombre(), estudiante.getPrimerApellido());
		int countDuplicateEmail = estudianteRepository.duplicateEmail(correoAux);

		if (countDuplicateEmail == 0) {
			estudiante.setCorreo(correo);
		} else {
			String modifiedEmail = CorreoGenerate.generarCorreo(correo, countDuplicateEmail);
			estudiante.setCorreo(modifiedEmail);
		}
		estudianteRepository.save(estudiante);
	}

	public void actualizarEstudiante(Long id, Estudiante estudianteUpdate) throws Exception {
		// Validar que el estudiante exista
		if (!estudianteRepository.existsById(id)) {
			throw new IllegalArgumentException("Estudiante no encontrado");
		}

		estudianteUpdate.setIdEstudiante(id);

		if (!estudianteRepository.update(estudianteUpdate)) {
			throw new RuntimeException("Error al actualizar en BD");
		}
	}

	public List<Estudiante> listarEstudiantes() throws Exception {
		// validaciones
		return estudianteRepository.findAll();
	}

	public void eliminarEstudiante(Long id) throws Exception {
		// Validar que el estudiante exista
		if (!estudianteRepository.existsById(id)) {
			throw new IllegalArgumentException("No se encontró el estudiante con ID: " + id);
		}

		// Validar que no tenga materias asignadas

		boolean delete = estudianteRepository.deleteById(id);
		if (!delete) {
			throw new RuntimeException("No se pudo eliminar el estudiante con ID: " + id);
		}
	}
}