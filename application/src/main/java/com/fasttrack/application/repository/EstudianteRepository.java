package com.fasttrack.application.repository;

import com.fasttrack.application.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class EstudianteRepository {
	
	private final JdbcTemplate jdbcTemplate;

    // Constructor explícito para inyectar JdbcTemplate
    public EstudianteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Estudiante> findAll() {
        String sql = "SELECT * FROM estudiante";
        return jdbcTemplate.query(sql, (rs, rowNum) -> 
            new Estudiante(
                rs.getLong("id_estudiante"),
                rs.getString("primer_nombre"),
                rs.getString("primer_apellido"),
                rs.getString("pais"),
                rs.getString("correo")
            ));
    }
    
    public void save(Estudiante estudiante) {
        String sql = "INSERT INTO estudiante (primer_nombre, primer_apellido, pais, correo) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
            estudiante.getPrimerNombre(),
            estudiante.getPrimerApellido(),
            estudiante.getPais(),
            estudiante.getCorreo()
        );
    }
	
}
