package com.fasttrack.application.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.fasttrack.application.model.Materia;

@Repository
public class MateriaRepository {

	private final DataSource dataSource;

	public MateriaRepository(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public List<Materia> findAll() throws SQLException {
		String query = "SELECT id_materia, nombre, codigo FROM materia";
		List<Materia> materias = new ArrayList<>();

		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				Materia materia = new Materia();
				materia.setId(rs.getLong("id_materia"));
				;
				materia.setNombre(rs.getString("nombre"));
				;
				materia.setCodigo(rs.getLong("codigo"));
				materias.add(materia);
			}
		}
		return materias;
	}

	public boolean save(Materia materia) throws SQLException {
		String query = "INSERT INTO materia (nombre, codigo) VALUES (?, ?)";

		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

			stmt.setString(1, materia.getNombre());
			stmt.setLong(2, materia.getCodigo());

			return stmt.executeUpdate() > 0;
		}
	}

	public boolean update(Materia materia) throws SQLException {
		String query = "UPDATE materia SET nombre = ?, codigo = ? WHERE id_materia = ?";

		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

			stmt.setString(1, materia.getNombre());
			stmt.setLong(2, materia.getCodigo());
			stmt.setLong(3, materia.getId());

			return stmt.executeUpdate() > 0;
		}
	}

	public boolean deleteById(Long id) throws SQLException {
		String query = "DELETE FROM materia WHERE id_materia = ?";

		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

			stmt.setLong(1, id);
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		}
	}

	public boolean existsByNombre(String nombre) throws SQLException {
		String query = "SELECT COUNT(*) FROM materia WHERE nombre = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, nombre);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
			return false;
		}
	}

	public boolean existsByCodigo(Long codigo) throws SQLException {
		String query = "SELECT COUNT(*) FROM materia WHERE codigo = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setLong(1, codigo);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
			return false;
		}
	}

	public boolean existsById(Long id) throws SQLException {
		String query = "SELECT COUNT(*) FROM materia WHERE id_materia = ?";

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
}
