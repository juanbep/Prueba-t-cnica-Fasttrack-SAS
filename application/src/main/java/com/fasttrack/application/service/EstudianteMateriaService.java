package com.fasttrack.application.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasttrack.application.model.Estudiante;
import com.fasttrack.application.model.Materia;
import com.fasttrack.application.repository.EstudianteMateriaRepository;
import com.fasttrack.application.repository.EstudianteRepository;
import com.fasttrack.application.repository.MateriaRepository;

@Service
public class EstudianteMateriaService {

	private final EstudianteMateriaRepository emRepository;
	private final EstudianteRepository estudianteRepository;
	private final MateriaRepository materiaRepository;

	public EstudianteMateriaService(EstudianteMateriaRepository emRepository, EstudianteRepository estudianteRepository,
			MateriaRepository materiaRepository) {
		this.emRepository = emRepository;
		this.estudianteRepository = estudianteRepository;
		this.materiaRepository = materiaRepository;
	}

	public void asignarMateria(Long estudianteId, Long materiaId) throws SQLException {

		if (!estudianteRepository.existsById(estudianteId)) {
			throw new IllegalArgumentException("Estudiante no encontrado");
		}

		if (!materiaRepository.existsById(materiaId)) {
			throw new IllegalArgumentException("Materia no encontrada");
		}

		emRepository.asignarMateria(estudianteId, materiaId);
	}
	
	public void desasignarMateria(Long estudianteId, Long materiaId) throws SQLException {
	   
		if (!estudianteRepository.existsById(estudianteId)) {
	        throw new IllegalArgumentException("Estudiante no encontrado");
	    }

	    if (!materiaRepository.existsById(materiaId)) {
	        throw new IllegalArgumentException("Materia no encontrada");
	    }

	    emRepository.desasignarMateria(estudianteId, materiaId);
	}
	
	public List<Materia> listarMateriasEstudiante(Long idEstudiante) throws SQLException {
		
	    if (!estudianteRepository.existsById(idEstudiante)) {
	        throw new IllegalArgumentException("Estudiante no encontrado");
	    }

	    return emRepository.listarMateriasAsignadas(idEstudiante);
	}
	
	public List<Estudiante> listarEstudiantesPorMateria(Long idMateria) throws SQLException {
	    if (!materiaRepository.existsById(idMateria)) {
	        throw new IllegalArgumentException("Materia no encontrada");
	    }

	    return emRepository.listarEstudiantesPorMateria(idMateria);
	}

}
