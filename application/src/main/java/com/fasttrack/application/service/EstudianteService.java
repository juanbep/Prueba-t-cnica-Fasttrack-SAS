package com.fasttrack.application.service;

import com.fasttrack.application.model.Estudiante;
import com.fasttrack.application.repository.EstudianteRepository;
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
}