package com.fasttrack.application.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.fasttrack.application.dto.schemas.AsignarMateriaRequest;
import com.fasttrack.application.dto.schemas.AsignarMateriaResponse;
import com.fasttrack.application.dto.schemas.DesasignarMateriaRequest;
import com.fasttrack.application.dto.schemas.DesasignarMateriaResponse;
import com.fasttrack.application.dto.schemas.EstudianteMateriaDTO;
import com.fasttrack.application.dto.schemas.ListaEstudiantesMateria;
import com.fasttrack.application.dto.schemas.ListaMateriasEstudiante;
import com.fasttrack.application.dto.schemas.ListarEstudiantesMateriaRequest;
import com.fasttrack.application.dto.schemas.ListarEstudiantesMateriaResponse;
import com.fasttrack.application.dto.schemas.ListarMateriasEstudianteRequest;
import com.fasttrack.application.dto.schemas.ListarMateriasEstudianteResponse;
import com.fasttrack.application.dto.schemas.MateriaEstudianteDTO;
import com.fasttrack.application.model.Estudiante;
import com.fasttrack.application.model.Materia;
import com.fasttrack.application.service.EstudianteMateriaService;

@Endpoint
public class MateriaEstudianteController {

	private static final String NAMESPACE_URI = "http://fasttrack.com/application/estudiante_materia";

	private final EstudianteMateriaService emService;
	private final ModelMapper modelMapper;

	public MateriaEstudianteController(EstudianteMateriaService emService, ModelMapper modelMapper) {
		this.emService = emService;
		this.modelMapper = modelMapper;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "AsignarMateriaRequest")
	@ResponsePayload
	public AsignarMateriaResponse asignarMateria(@RequestPayload AsignarMateriaRequest request) {
		AsignarMateriaResponse response = new AsignarMateriaResponse();

		try {
			emService.asignarMateria(request.getIdEstudiante(), request.getIdMateria());
			response.setExito(true);
			response.setMensaje("Materia asignada correctamente");
		} catch (IllegalArgumentException e) {
			response.setExito(false);
			response.setMensaje("Error: " + e.getMessage());
		} catch (Exception e) {
			response.setExito(false);
			response.setMensaje("Error inesperado: " + e.getMessage());
		}

		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "DesasignarMateriaRequest")
	@ResponsePayload
	public DesasignarMateriaResponse desasignarMateria(@RequestPayload DesasignarMateriaRequest request) {
		DesasignarMateriaResponse response = new DesasignarMateriaResponse();

		try {
			emService.desasignarMateria(request.getIdEstudiante(), request.getIdMateria());
			response.setExito(true);
			response.setMensaje("Materia desasignada correctamente.");
		} catch (IllegalArgumentException e) {
			response.setExito(false);
			response.setMensaje("Error: " + e.getMessage());
		} catch (Exception e) {
			response.setExito(false);
			response.setMensaje("Error inesperado: " + e.getMessage());
		}

		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "ListarMateriasEstudianteRequest")
	@ResponsePayload
	public ListarMateriasEstudianteResponse listarMateriasEstudiante(@RequestPayload ListarMateriasEstudianteRequest request) {
	    ListarMateriasEstudianteResponse response = new ListarMateriasEstudianteResponse();

	    try {
	        List<Materia> materiasEstudianteRepository = emService.listarMateriasEstudiante(request.getIdEstudiante());
	        ListaMateriasEstudiante listaMateriasEstudiante = new ListaMateriasEstudiante();
	        materiasEstudianteRepository.forEach(est -> {
	        	MateriaEstudianteDTO MateriaEstudianteDTO = modelMapper.map(est, MateriaEstudianteDTO.class);
	        	listaMateriasEstudiante.getMaterias().add(MateriaEstudianteDTO);
	        });
	       
	        response.setMaterias(listaMateriasEstudiante);
	        
	    } catch (Exception e) {
	    	throw new RuntimeException("Error al listar materias de estudiante: " + e.getMessage());
	    }

	    return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "ListarEstudiantesMateriaRequest")
	@ResponsePayload
	public ListarEstudiantesMateriaResponse listarEstudiantesMateria(@RequestPayload ListarEstudiantesMateriaRequest request) {
	    ListarEstudiantesMateriaResponse response = new ListarEstudiantesMateriaResponse();

	    try {
	        List<Estudiante> estudiantes = emService.listarEstudiantesPorMateria(request.getIdMateria());
	        ListaEstudiantesMateria listaEstudiantes = new ListaEstudiantesMateria();

	        estudiantes.forEach(est -> {
	            EstudianteMateriaDTO estudianteDTO = modelMapper.map(est, EstudianteMateriaDTO.class);
	            listaEstudiantes.getEstudiantes().add(estudianteDTO);
	        });

	        response.setEstudiantes(listaEstudiantes);

	    } catch (Exception e) {
	        throw new RuntimeException("Error al listar estudiantes por materia: " + e.getMessage(), e);
	    }

	    return response;
	}

}
