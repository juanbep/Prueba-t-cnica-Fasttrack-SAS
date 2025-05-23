package com.fasttrack.application.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.fasttrack.application.dto.schemas.ActualizarMateriaRequest;
import com.fasttrack.application.dto.schemas.ActualizarMateriaResponse;
import com.fasttrack.application.dto.schemas.EliminarMateriaRequest;
import com.fasttrack.application.dto.schemas.EliminarMateriaResponse;
import com.fasttrack.application.dto.schemas.ListaMaterias;
import com.fasttrack.application.dto.schemas.ListarMateriasRequest;
import com.fasttrack.application.dto.schemas.ListarMateriasResponse;
import com.fasttrack.application.dto.schemas.MateriaDTO;
import com.fasttrack.application.dto.schemas.RegistrarMateriaRequest;
import com.fasttrack.application.dto.schemas.RegistrarMateriaResponse;
import com.fasttrack.application.model.Materia;
import com.fasttrack.application.service.MateriaService;

@Endpoint
public class MateriaController {

	private static final String NAMESPACE_URI = "http://fasttrack.com/application/materia";

	private final MateriaService materiaService;
	private final ModelMapper modelMapper;

	public MateriaController(MateriaService materiaService, ModelMapper modelMapper) {
		this.materiaService = materiaService;
		this.modelMapper = modelMapper;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "ListarMateriasRequest")
	@ResponsePayload
	public ListarMateriasResponse listarMaterias(@RequestPayload ListarMateriasRequest request) {
		ListarMateriasResponse response = new ListarMateriasResponse();
		try {
			List<Materia> estudiantesRepository = materiaService.listarMaterias();
			ListaMaterias listaMaterias = new ListaMaterias();

			estudiantesRepository.forEach(est -> {
				MateriaDTO materiaDTO = modelMapper.map(est, MateriaDTO.class);
				listaMaterias.getMateria().add(materiaDTO);
			});

			response.setMaterias(listaMaterias);

		} catch (Exception e) {
			throw new RuntimeException("Error al listar materias: " + e.getMessage());
		}
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "RegistrarMateriaRequest")
	@ResponsePayload
	public RegistrarMateriaResponse registrarMateria(@RequestPayload RegistrarMateriaRequest request) {
		RegistrarMateriaResponse response = new RegistrarMateriaResponse();
		Materia materia = new Materia();
		materia.setNombre(request.getNombre());
		materia.setCodigo(request.getCodigo());

		try {
			materiaService.registrarMateria(materia);
			response.setExito(true);
			response.setMensaje("Materia registrada exitosamente.");
		} catch (Exception e) {
			response.setExito(false);
			response.setMensaje("Error al registrar Materia: " + e.getMessage());
		}

		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "ActualizarMateriaRequest")
	@ResponsePayload
	public ActualizarMateriaResponse actualizarMateria(@RequestPayload ActualizarMateriaRequest request) {
		ActualizarMateriaResponse response = new ActualizarMateriaResponse();
		Materia materiaUpdate = new Materia();
		materiaUpdate.setNombre(request.getNombre());
		materiaUpdate.setCodigo(request.getCodigo());

		try {
			materiaService.actualizarMateria(request.getId(), materiaUpdate);
			response.setExito(true);
			response.setMensaje("Materia actualizada exitosamente.");

		} catch (IllegalArgumentException e) {
			response.setExito(false);
			response.setMensaje(e.getMessage());
		} catch (Exception e) {
			response.setExito(false);
			response.setMensaje("Error al actualizar materia: " + e.getMessage());
		}

		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "EliminarMateriaRequest")
	@ResponsePayload
	public EliminarMateriaResponse eliminarMateria(@RequestPayload EliminarMateriaRequest request) {
		EliminarMateriaResponse response = new EliminarMateriaResponse();

		try {
			materiaService.eliminarMateria(request.getId());
			response.setExito(true);
			response.setMensaje("Materia eliminada exitosamente.");
		} catch (IllegalArgumentException e) {
			response.setExito(false);
			response.setMensaje(e.getMessage());
		} catch (Exception e) {
			response.setExito(false);
			response.setMensaje("Error al eliminar materia: " + e.getMessage());
		}

		return response;
	}

}
