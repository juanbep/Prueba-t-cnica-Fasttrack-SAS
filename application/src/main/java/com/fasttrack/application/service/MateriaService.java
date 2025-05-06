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
		Materia actual = materiaRepository.findById(id);

		if (actual == null) {
			throw new IllegalArgumentException("Materia no encontrada");
		}

		boolean nombreCambio = !actual.getNombre().equals(materiaUpdate.getNombre());
		boolean codigoCambio = !actual.getCodigo().equals(materiaUpdate.getCodigo());

		if (!nombreCambio && !codigoCambio) {
			throw new RuntimeException("No se actualizó ningún campo");
		}

		if (nombreCambio && materiaRepository.existsByNombre(materiaUpdate.getNombre())) {
			throw new Exception("Ya existe una materia con ese nombre");
		}

		if (codigoCambio && materiaRepository.existsByCodigo(materiaUpdate.getCodigo())) {
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

	public void eliminarMateria(Long id) throws Exception {

		if (!materiaRepository.existsById(id)) {
			throw new IllegalArgumentException("No se encontró la materia con ID: " + id);
		}

		// validaciones
		// Validar que no tenga materias asignadas

		boolean delete = materiaRepository.deleteById(id);
		if (!delete) {
			throw new RuntimeException("No se pudo eliminar la materia con ID: " + id);
		}
	}

}
