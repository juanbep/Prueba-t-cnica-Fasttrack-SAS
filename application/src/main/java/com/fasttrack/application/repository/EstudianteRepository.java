package com.fasttrack.application.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.fasttrack.application.model.Estudiante;

@Repository
public class EstudianteRepository {

	private final DataSource dataSource;

	// Constructor explícito para inyectar el dataSource
	public EstudianteRepository(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public List<Estudiante> findAll() throws SQLException {
		String query = "SELECT id_estudiante, primer_nombre, primer_apellido, pais, correo FROM estudiante";
		List<Estudiante> estudiantes = new ArrayList<>();

		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				Estudiante estudiante = new Estudiante();
				estudiante.setId(rs.getLong("id_estudiante"));
				estudiante.setPrimerNombre(rs.getString("primer_nombre"));
				estudiante.setPrimerApellido(rs.getString("primer_apellido"));
				estudiante.setPais(rs.getString("pais"));
				estudiante.setCorreo(rs.getString("correo"));
				estudiantes.add(estudiante);
			}
		}
		return estudiantes;
	}

	public boolean save(Estudiante estudiante) throws SQLException {
		String query = "INSERT INTO estudiante (primer_nombre, primer_apellido, pais, correo) VALUES (?, ?, ?, ?)";

		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

			stmt.setString(1, estudiante.getPrimerNombre());
			stmt.setString(2, estudiante.getPrimerApellido());
			stmt.setString(3, estudiante.getPais());
			stmt.setString(4, estudiante.getCorreo());

			return stmt.executeUpdate() > 0;
		}
	}

	public boolean update(Estudiante estudiante) throws SQLException {
		String query = "UPDATE estudiante SET primer_nombre = ?, primer_apellido = ?, pais = ?, correo = ? WHERE id_estudiante = ?";

		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

			stmt.setString(1, estudiante.getPrimerNombre());
			stmt.setString(2, estudiante.getPrimerApellido());
			stmt.setString(3, estudiante.getPais());
			stmt.setString(4, estudiante.getCorreo());
			stmt.setLong(5, estudiante.getId());

			return stmt.executeUpdate() > 0;
		}
	}

	public boolean deleteById(Long id) throws SQLException {
		String query = "DELETE FROM estudiante WHERE id_estudiante = ?";

		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

			stmt.setLong(1, id);
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		}
	}

	public Estudiante findById(Long id) throws SQLException {
		String query = "SELECT * FROM estudiante WHERE id_estudiante = ?";

		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

			stmt.setLong(1, id);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					Estudiante estudiante = new Estudiante();
					estudiante.setPrimerNombre(rs.getString("primer_nombre"));
					estudiante.setPrimerApellido(rs.getString("primer_apellido"));
					estudiante.setPais(rs.getString("pais"));
					estudiante.setCorreo(rs.getString("correo"));
					return estudiante;
				} else {
					return null;
				}
			}
		}
	}

	public boolean existsById(Long id) throws SQLException {
		String query = "SELECT COUNT(*) FROM estudiante WHERE id_estudiante = ?";

		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

			stmt.setLong(1, id);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) > 0;
				}
				return false;
			}
		}
	}

	public String correoDuplicado(String correoBase, String dominio, String pais) throws SQLException {
	    String query = "SELECT correo FROM estudiante WHERE correo LIKE ? AND correo LIKE ? ORDER BY id_estudiante DESC LIMIT 1";

	    try (Connection conn = dataSource.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(query)) {

	        stmt.setString(1, correoBase + "%@" + dominio + "." + pais);
	        stmt.setString(2, "%@" + dominio + "." + pais);              

	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                return rs.getString("correo");
	            }
	        }
	    }

	    return null;
	}
}
