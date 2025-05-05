package com.fasttrack.application.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasttrack.application.model.Estudiante;
import com.fasttrack.application.repository.EstudianteRepository;
import com.fasttrack.application.utilities.CorreoGenerate;

@Service
public class EstudianteService {

	private final EstudianteRepository estudianteRepository;

	public EstudianteService(EstudianteRepository estudianteRepository) {
		this.estudianteRepository = estudianteRepository;
	}

	public void registrarEstudiante(Estudiante estudiante) throws Exception {
		// validaciones

		//System.out.println(estudiante.getPais());
		estudiante.setCorreo(
				asignarCorreo(estudiante.getPrimerNombre(), estudiante.getPrimerApellido(), estudiante.getPais().toLowerCase()));

		if (!estudianteRepository.save(estudiante)) {
			throw new RuntimeException("Error al actualizar en BD");
		}
	}

	public void actualizarEstudiante(Long id, Estudiante estudianteUpdate) throws Exception {
		// Validar si existe
		if (!estudianteRepository.existsById(id)) {
			throw new IllegalArgumentException("Estudiante no encontrado");
		}

		Estudiante actual = estudianteRepository.findById(id); 
		
		boolean datosClaveCambiaron = 
				!actual.getPrimerNombre().equals(estudianteUpdate.getPrimerNombre()) ||
				!actual.getPrimerApellido().equals(estudianteUpdate.getPrimerApellido()) ||
				!actual.getPais().equals(estudianteUpdate.getPais());
		
		if (datosClaveCambiaron) {
			String nuevoCorreo = asignarCorreo(estudianteUpdate.getPrimerNombre(), estudianteUpdate.getPrimerApellido(), estudianteUpdate.getPais().toLowerCase());
			estudianteUpdate.setCorreo(nuevoCorreo);
			estudianteUpdate.setId(id);

			if (!estudianteRepository.update(estudianteUpdate)) {
				throw new RuntimeException("Error al actualizar en BD");
			}
		} else {
			throw new RuntimeException("No se actualizó ningun campo...");
		}
		
		
	}

	public List<Estudiante> listarEstudiantes() throws Exception {
		// validaciones
		return estudianteRepository.findAll();
	}

	public void eliminarEstudiante(Long id) throws Exception {

		if (!estudianteRepository.existsById(id)) {
			throw new IllegalArgumentException("No se encontró el estudiante con ID: " + id);
		}

		// validaciones
		// Validar que no tenga materias asignadas

		boolean delete = estudianteRepository.deleteById(id);
		if (!delete) {
			throw new RuntimeException("No se pudo eliminar el estudiante con ID: " + id);
		}
	}

	private String asignarCorreo(String primerNombre, String primerApellido, String pais) throws SQLException {
	    String correoBase = CorreoGenerate.generarCorreo(primerNombre, primerApellido); // "primer_nombre.primer_apellido"
	    String dominio = "fasttrack.com";
	    String correoCompleto = correoBase + "@" + dominio + "." + pais;

	    String correoDuplicado = estudianteRepository.correoDuplicado(correoBase, dominio, pais);

	    if (correoDuplicado == null) {
	        return correoCompleto;
	    }

	    int arrobaIndex = correoDuplicado.indexOf('@'); // índice de @
	    String localPart = correoDuplicado.substring(0, arrobaIndex); // "extrae -> primer_nombre.primer_apellido.sufijo"

	    if (localPart.equals(correoBase)) {
	        return correoBase + ".2@" + dominio + "." + pais;
	    } else if (localPart.startsWith(correoBase + ".")) {
	        String sufijoStr = localPart.substring((correoBase + ".").length());
	        try {
	            int sufijo = Integer.parseInt(sufijoStr);
	            return correoBase + "." + (sufijo + 1) + "@" + dominio + "." + pais;
	        } catch (NumberFormatException e) {
	            return correoBase + ".2@" + dominio + "." + pais;
	        }
	    }

	    return correoBase + ".2@" + dominio + "." + pais;
	}

}




