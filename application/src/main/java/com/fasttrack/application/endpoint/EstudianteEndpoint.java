package com.fasttrack.application.endpoint;

import com.fasttrack.application.model.Estudiante;
import com.fasttrack.application.service.EstudianteService;
import com.fasttrack.application.dto.schemas.RegistrarEstudianteRequest;
import com.fasttrack.application.dto.schemas.RegistrarEstudianteResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;


@Endpoint
public class EstudianteEndpoint {
	
	private static final String NAMESPACE_URI = "http://fasttrack.com/application/schemas";
	
	private final EstudianteService estudianteService;

    public EstudianteEndpoint(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }
    
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "RegistrarEstudianteRequest")
    @ResponsePayload
    public RegistrarEstudianteResponse registrarEstudiante(@RequestPayload RegistrarEstudianteRequest request) {
        RegistrarEstudianteResponse response = new RegistrarEstudianteResponse();
        Estudiante estudiante = new Estudiante();
        estudiante.setPrimerNombre(request.getPrimerNombre());
        estudiante.setPrimerApellido(request.getPrimerApellido());
        estudiante.setPais(request.getPais());
        estudiante.setCorreo(request.getCorreo());

        try {
            estudianteService.registrarEstudiante(estudiante);
            response.setExito(true);
            response.setMensaje("Estudiante registrado exitosamente.");
        } catch (Exception e) {
            response.setExito(false);
            response.setMensaje("Error al registrar el estudiante: " + e.getMessage());
        }

        return response;
    }
	
}
