package com.fasttrack.application.service;

import com.fasttrack.application.model.Estudiante;
import com.fasttrack.application.repository.EstudianteRepository;

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