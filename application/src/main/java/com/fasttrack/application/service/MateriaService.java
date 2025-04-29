package com.fasttrack.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fasttrack.application.model.Materia;
import com.fasttrack.application.repository.MateriaRepository;

@Service
public class MateriaService {

	private final MateriaRepository materiaRepository;

	public MateriaService(MateriaRepository materiaRepository) {
		this.materiaRepository = materiaRepository;
	}

	public void registrarMateria(Materia materia) throws Exception {
		// validaciones
		if (materiaRepository.existsByNombre(materia.getNombre())) {
			throw new Exception("Ya existe una materia con ese nombre");
		}
		if (materiaRepository.existsByCodigo(materia.getCodigo())) {
			throw new Exception("Ya existe una materia con ese código");
		}

		if (!materiaRepository.save(materia)) {
			throw new RuntimeException("Error al actualizar en BD");
		}
	}

	public void actualizarMateria(Long id, Materia materiaUpdate) throws Exception {
		// Validar si existe
		if (!materiaRepository.existsById(id)) {
			throw new IllegalArgumentException("Materia no encontrada");
		}

		// Validaciones
		if (materiaRepository.existsByNombre(materiaUpdate.getNombre())) {
			throw new Exception("Ya existe una materia con ese nombre");
		}
		if (materiaRepository.existsByCodigo(materiaUpdate.getCodigo())) {
			throw new Exception("Ya existe una materia con ese código");
		}

		materiaUpdate.setId(id);

		if (!materiaRepository.update(materiaUpdate)) {
			throw new RuntimeException("Error al actualizar en BD");
		}

	}

	public List<Materia> listarMaterias() throws Exception {
		// validaciones
		return materiaRepository.findAll();
	}

}
