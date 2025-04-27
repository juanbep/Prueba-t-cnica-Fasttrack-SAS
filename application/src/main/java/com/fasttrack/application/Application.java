package com.fasttrack.application;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasttrack.application.model.Estudiante;
import com.fasttrack.application.repository.EstudianteRepository;

@SpringBootApplication
public class Application implements CommandLineRunner{
	
	private final EstudianteRepository estudianteRepository;
	
	public Application(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Insertar un estudiante de prueba
        Estudiante estudiante = new Estudiante();
        estudiante.setPrimerNombre("PEPE");
        estudiante.setPrimerApellido("GOMEZ");
        estudiante.setPais("CO");
        estudiante.setCorreo("GOMEZ.PEPE@fasttrack.com.co");
        estudianteRepository.save(estudiante);

        // Listar todos los estudiantes
        // System.out.println("Estudiantes: " + estudianteRepository.findAll());
	}
	
	

}
