package com.fasttrack.application.controller;

import com.fasttrack.application.model.Estudiante;
import com.fasttrack.application.service.EstudianteService;
import com.fasttrack.application.dto.schemas.EstudianteDTO;
import com.fasttrack.application.dto.schemas.EliminarEstudianteRequest;
import com.fasttrack.application.dto.schemas.EliminarEstudianteResponse;
import com.fasttrack.application.dto.schemas.ListaEstudiantes;
import com.fasttrack.application.dto.schemas.ListarEstudiantesRequest;
import com.fasttrack.application.dto.schemas.ListarEstudiantesResponse;
import com.fasttrack.application.dto.schemas.RegistrarEstudianteRequest;
import com.fasttrack.application.dto.schemas.RegistrarEstudianteResponse;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

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
            throw new RuntimeException("Error al listar estudiantes: " + e.getMessage());
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
    
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "EliminarEstudianteRequest")
    @ResponsePayload
    public EliminarEstudianteResponse eliminarEstudiante(@RequestPayload EliminarEstudianteRequest request) {
        EliminarEstudianteResponse response = new EliminarEstudianteResponse();
        
        try {
            estudianteService.eliminarEstudiante(request.getId());
            response.setExito(true);
            response.setMensaje("Estudiante eliminado correctamente");
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
