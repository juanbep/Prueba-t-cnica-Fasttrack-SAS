package com.fasttrack.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasttrack.application.repository.EstudianteRepository;
import com.fasttrack.application.service.EstudianteService;

public class AsignarCorreoTest {

    private EstudianteRepository estudianteRepository;
    private EstudianteService estudianteService;

    @BeforeEach
    void setUp() {
        estudianteRepository = mock(EstudianteRepository.class);
        estudianteService = new EstudianteService(estudianteRepository);
    }

    @Test
    void testCorreoSinDuplicados() throws Exception {
        when(estudianteRepository.correoDuplicado("david.ruiz", "fasttrack.com", "co")).thenReturn(null);

        String correo = estudianteService.asignarCorreo("david", "ruiz", "co");

        assertEquals("david.ruiz@fasttrack.com.co", correo);
    }

    @Test
    void testCorreoDuplicadoUnaVez() throws Exception {
        when(estudianteRepository.correoDuplicado("david.ruiz", "fasttrack.com", "co")).thenReturn("david.ruiz@fasttrack.com.co");

        String correo = estudianteService.asignarCorreo("david", "ruiz", "co");

        assertEquals("david.ruiz.2@fasttrack.com.co", correo);
    }
    
    @Test
    void testCorreoDuplicadoDosVeces() throws Exception {
        when(estudianteRepository.correoDuplicado("david.ruiz", "fasttrack.com", "co")).thenReturn("david.ruiz.2@fasttrack.com.co");

        String correo = estudianteService.asignarCorreo("david", "ruiz", "co");

        assertEquals("david.ruiz.3@fasttrack.com.co", correo);
    }
    
    @Test
    void testCorreoDuplicadoTresVeces() throws Exception {
        when(estudianteRepository.correoDuplicado("david.ruiz", "fasttrack.com", "co")).thenReturn("david.ruiz.3@fasttrack.com.co");

        String correo = estudianteService.asignarCorreo("david", "ruiz", "co");

        assertEquals("david.ruiz.4@fasttrack.com.co", correo);
    }
    
    @Test
    void testCorreoDuplicadoCuatroVeces() throws Exception {
        when(estudianteRepository.correoDuplicado("david.ruiz", "fasttrack.com", "co")).thenReturn("david.ruiz.4@fasttrack.com.co");

        String correo = estudianteService.asignarCorreo("david", "ruiz", "co");

        assertEquals("david.ruiz.5@fasttrack.com.co", correo);
    }
    
    @Test
    void testCorreoDuplicadoCincoVeces() throws Exception {
        when(estudianteRepository.correoDuplicado("david.ruiz", "fasttrack.com", "co")).thenReturn("david.ruiz.5@fasttrack.com.co");

        String correo = estudianteService.asignarCorreo("david", "ruiz", "co");

        assertEquals("david.ruiz.7@fasttrack.com.co", correo); //error
    }
    
    
    

    // Puedes agregar más pruebas similares
}
