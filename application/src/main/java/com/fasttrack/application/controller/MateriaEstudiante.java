package com.fasttrack.application.controller;

import org.modelmapper.ModelMapper;
import org.springframework.ws.server.endpoint.annotation.Endpoint;

import com.fasttrack.application.dto.schemas.AsignarMateriaRequest;
import com.fasttrack.application.dto.schemas.AsignarMateriaResponse;
import com.fasttrack.application.service.EstudianteMateriaService;

import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class MateriaEstudiante {

	private static final String NAMESPACE_URI = "http://fasttrack.com/application/estudiante_materia";

	private final EstudianteMateriaService emService;
	private final ModelMapper modelMapper;

	public MateriaEstudiante(EstudianteMateriaService emService, ModelMapper modelMapper) {
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

}
