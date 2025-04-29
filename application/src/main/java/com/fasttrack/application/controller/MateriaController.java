package com.fasttrack.application.controller;

import org.modelmapper.ModelMapper;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

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
	
}
