package com.fasttrack.application.repository;

import com.fasttrack.application.model.Estudiante;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class EstudianteRepository {
	
	private final DataSource dataSource;

    // Constructor explícito para inyectar el dataSource
	public EstudianteRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

	public void save(Estudiante estudiante) throws SQLException {
        String sql = "INSERT INTO estudiante (primer_nombre, primer_apellido, pais, correo) VALUES (?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, estudiante.getPrimerNombre());
            stmt.setString(2, estudiante.getPrimerApellido());
            stmt.setString(3, estudiante.getPais());
            stmt.setString(4, estudiante.getCorreo());

            stmt.executeUpdate();
        }
    }
	
}
