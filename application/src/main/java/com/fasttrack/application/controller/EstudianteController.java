package com.fasttrack.application.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.fasttrack.application.dto.schemas.ActualizarEstudianteRequest;
import com.fasttrack.application.dto.schemas.ActualizarEstudianteResponse;
import com.fasttrack.application.dto.schemas.EliminarEstudianteRequest;
import com.fasttrack.application.dto.schemas.EliminarEstudianteResponse;
import com.fasttrack.application.dto.schemas.EstudianteDTO;
import com.fasttrack.application.dto.schemas.ListaEstudiantes;
import com.fasttrack.application.dto.schemas.ListarEstudiantesRequest;
import com.fasttrack.application.dto.schemas.ListarEstudiantesResponse;
import com.fasttrack.application.dto.schemas.RegistrarEstudianteRequest;
import com.fasttrack.application.dto.schemas.RegistrarEstudianteResponse;
import com.fasttrack.application.model.Estudiante;
import com.fasttrack.application.service.EstudianteService;
import com.fasttrack.application.utilities.PaisEnum;

@Endpoint
public class EstudianteController {

	private static final String NAMESPACE_URI = "http://fasttrack.com/application/estudiante";

	private final EstudianteService estudianteService;
	private final ModelMapper modelMapper;

	public EstudianteController(EstudianteService estudianteService, ModelMapper modelMapper) {
		this.estudianteService = estudianteService;
		this.modelMapper = modelMapper;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "ListarEstudiantesRequest")
	@ResponsePayload
	public ListarEstudiantesResponse listarEstudiantes(@RequestPayload ListarEstudiantesRequest request) {
		ListarEstudiantesResponse response = new ListarEstudiantesResponse();
		try {
			List<Estudiante> estudiantesRepository = estudianteService.listarEstudiantes();
			ListaEstudiantes listaEstudiantes = new ListaEstudiantes();

			estudiantesRepository.forEach(est -> {
				EstudianteDTO estudianteDTO = modelMapper.map(est, EstudianteDTO.class);
				listaEstudiantes.getEstudiante().add(estudianteDTO);
			});

			response.setEstudiantes(listaEstudiantes);

		} catch (Exception e) {
			throw new RuntimeException("Error al listar los estudiantes: " + e.getMessage());
		}
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "RegistrarEstudianteRequest")
	@ResponsePayload
	public RegistrarEstudianteResponse registrarEstudiante(@RequestPayload RegistrarEstudianteRequest request) {
		RegistrarEstudianteResponse response = new RegistrarEstudianteResponse();
		Estudiante estudiante = new Estudiante();
		estudiante.setPrimerNombre(request.getPrimerNombre());
		estudiante.setPrimerApellido(request.getPrimerApellido());
		
		try {
			String codigoISO = PaisEnum.obtenerCodigoISO(request.getPais());
	        estudiante.setPais(codigoISO);   
			estudianteService.registrarEstudiante(estudiante);
			response.setExito(true);
			response.setMensaje("Estudiante registrado exitosamente.");
		} catch (Exception e) {
			response.setExito(false);
			response.setMensaje("Error al registrar el estudiante: " + e.getMessage());
		}

		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "ActualizarEstudianteRequest")
	@ResponsePayload
	public ActualizarEstudianteResponse actualizarEstudiante(@RequestPayload ActualizarEstudianteRequest request) {
		ActualizarEstudianteResponse response = new ActualizarEstudianteResponse();
		Estudiante estudianteUpdate = new Estudiante();
		estudianteUpdate.setPrimerNombre(request.getPrimerNombre());
		estudianteUpdate.setPrimerApellido(request.getPrimerApellido());
		
		try {
			String codigoISO = PaisEnum.obtenerCodigoISO(request.getPais());
			estudianteUpdate.setPais(codigoISO);
			estudianteService.actualizarEstudiante(request.getId(), estudianteUpdate);
			response.setExito(true);
			response.setMensaje("Estudiante actualizado exitosamente.");

		} catch (IllegalArgumentException e) {
			response.setExito(false);
			response.setMensaje(e.getMessage());
		} catch (Exception e) {
			response.setExito(false);
			response.setMensaje("Error al actualizar el estudiante: " + e.getMessage());
		}

		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "EliminarEstudianteRequest")
	@ResponsePayload
	public EliminarEstudianteResponse eliminarEstudiante(@RequestPayload EliminarEstudianteRequest request) {
		EliminarEstudianteResponse response = new EliminarEstudianteResponse();

		try {
			estudianteService.eliminarEstudiante(request.getId());
			response.setExito(true);
			response.setMensaje("Estudiante eliminado exitosamente.");
		} catch (IllegalArgumentException e) {
			response.setExito(false);
			response.setMensaje(e.getMessage());
		} catch (Exception e) {
			response.setExito(false);
			response.setMensaje("Error al eliminar el estudiante: " + e.getMessage());
		}

		return response;
	}

}
