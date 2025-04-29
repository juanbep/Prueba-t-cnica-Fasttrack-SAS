package com.fasttrack.application.controller;

import java.sql.SQLException;

import org.modelmapper.ModelMapper;
import org.springframework.ws.server.endpoint.annotation.Endpoint;

import com.fasttrack.application.dto.schemas.AsignarMateriaRequest;
import com.fasttrack.application.dto.schemas.AsignarMateriaResponse;
import com.fasttrack.application.dto.schemas.DesasignarMateriaRequest;
import com.fasttrack.application.dto.schemas.DesasignarMateriaResponse;
import com.fasttrack.application.service.EstudianteMateriaService;

import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

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

	

}
