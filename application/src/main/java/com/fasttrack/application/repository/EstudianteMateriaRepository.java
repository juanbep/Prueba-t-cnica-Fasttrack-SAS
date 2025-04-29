package com.fasttrack.application.repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.fasttrack.application.model.Materia;
import com.fasttrack.application.model.Estudiante;

@Repository
public class EstudianteMateriaRepository {

	private final DataSource dataSource;

	// Constructor explícito para inyectar el dataSource
	public EstudianteMateriaRepository(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void asignarMateria(long idEstudiante, long idMateria) {
		String query = "INSERT INTO estudiante_materia (id_estudiante, id_materia) VALUES (?, ?)";

		try (Connection conn = dataSource.getConnection();
				PreparedStatement insertStmt = conn.prepareStatement(query)) {

			insertStmt.setLong(1, idEstudiante);
			insertStmt.setLong(2, idMateria);
			insertStmt.executeUpdate();

		} catch (SQLException e) {
			if (e.getSQLState().startsWith("23")) { // código de violación de integridad
				throw new RuntimeException("La materia ya está asignada al estudiante.");
			}
			throw new RuntimeException("Error al asignar materia: " + e.getMessage(), e);
		}
	}

	public void desasignarMateria(long idEstudiante, long idMateria) {
		String query = "DELETE FROM estudiante_materia WHERE id_estudiante = ? AND id_materia = ?";

		try (Connection conn = dataSource.getConnection();
				PreparedStatement deleteStmt = conn.prepareStatement(query)) {

			deleteStmt.setLong(1, idEstudiante);
			deleteStmt.setLong(2, idMateria);
			int rowsAffected = deleteStmt.executeUpdate();

			if (rowsAffected == 0) {
				throw new RuntimeException("La materia no está asignada al estudiante.");
			}

		} catch (SQLException e) {
			throw new RuntimeException("Error al desasignar materia: " + e.getMessage(), e);
		}
	}

	public List<Materia> listarMateriasAsignadas(long idEstudiante) {
		String query = """
				    SELECT m.id_materia, m.nombre, m.codigo
				    FROM materia m
				    INNER JOIN estudiante_materia em ON m.id_materia = em.id_materia
				    WHERE em.id_estudiante = ?
				""";

		List<Materia> materias = new ArrayList<>();

		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

			stmt.setLong(1, idEstudiante);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Materia materia = new Materia();
				materia.setId(rs.getLong("id_materia"));
				materia.setNombre(rs.getString("nombre"));
				materia.setCodigo(rs.getLong("codigo"));
				materias.add(materia);
			}

		} catch (SQLException e) {
			throw new RuntimeException("Error al listar materias del estudiante", e);
		}

		return materias;
	}
	
	public List<Estudiante> listarEstudiantesPorMateria(long idMateria) {
	    String query = """
	        SELECT e.id_estudiante, e.primer_nombre, e.primer_apellido
	        FROM estudiante e
	        INNER JOIN estudiante_materia em ON e.id_estudiante = em.id_estudiante
	        WHERE em.id_materia = ?
	    """;

	    List<Estudiante> estudiantes = new ArrayList<>();

	    try (Connection conn = dataSource.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(query)) {

	        stmt.setLong(1, idMateria);
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            Estudiante estudiante = new Estudiante();
	            estudiante.setId(rs.getLong("id_estudiante"));
	            estudiante.setPrimerNombre(rs.getString("primer_nombre"));
	            estudiante.setPrimerApellido(rs.getString("primer_apellido"));
	            estudiantes.add(estudiante);
	        }

	    } catch (SQLException e) {
	        throw new RuntimeException("Error al listar estudiantes por materia", e);
	    }

	    return estudiantes;
	}

}
