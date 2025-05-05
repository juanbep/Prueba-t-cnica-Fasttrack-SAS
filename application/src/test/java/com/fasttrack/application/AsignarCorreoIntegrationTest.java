package com.fasttrack.application;

import com.fasttrack.application.service.EstudianteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AsignarCorreoIntegrationTest {
	
	 @Autowired
	    private EstudianteService estudianteService;

	    @Test
	    void testNoExistenDuplicados() throws Exception {

	        String correoGenerado = estudianteService.asignarCorreo("JUAN", "BECA", "co");

	        assertEquals("juan.beca@fasttrack.com.co", correoGenerado);
	    }
	    
	    @Test
	    void testCorreoDuplicado() throws Exception {

	        String correoGenerado = estudianteService.asignarCorreo("LUISA", "CANO", "co");

	        assertEquals("luisa.cano.2@fasttrack.com.co", correoGenerado);
	    }
	    
	    @Test
	    void testCorreoDuplicadoDosVeces() throws Exception {

	        String correoGenerado = estudianteService.asignarCorreo("DAVID", "RUIZ", "co");

	        assertEquals("david.ruiz.3@fasttrack.com.co", correoGenerado);
	    }
	    
	    @Test
	    void testCorreoDuplicadoDistintoPais() throws Exception {

	        String correoGenerado = estudianteService.asignarCorreo("DAVID", "RUIZ", "mx");

	        assertEquals("david.ruiz.2@fasttrack.com.mx", correoGenerado);
	    }
	    
	    @Test
	    void testCorreoDuplicadoConHuecosEnLaSeuencia() throws Exception {

	        String correoGenerado = estudianteService.asignarCorreo("CARLOS", "GOMEZ", "co");

	        assertEquals("carlos.gomez.4@fasttrack.com.co", correoGenerado);
	    }
	    
	    @Test
	    void testMismoNombreDiferenteApellido() throws Exception {

	        String correoGenerado = estudianteService.asignarCorreo("CARLOS", "CHAUX", "co");

	        assertEquals("carlos.chaux@fasttrack.com.co", correoGenerado);
	    }
	    
	    @Test
	    void testMismoApellidoDiferenteNombre() throws Exception {

	        String correoGenerado = estudianteService.asignarCorreo("FELIPE", "GOMEZ", "co");

	        assertEquals("felipe.gomez@fasttrack.com.co", correoGenerado);
	    }
	    
	    
	    
	    
}
