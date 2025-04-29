package com.fasttrack.application.service;

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

		if (!materiaRepository.save(materia)) {
			throw new RuntimeException("Error al actualizar en BD");
		}
	}

}
