package com.fasttrack.application.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.fasttrack.application.model.Materia;
import org.springframework.stereotype.Repository;

@Repository
public class MateriaRepository {

	private final DataSource dataSource;

	public MateriaRepository(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public boolean save(Materia materia) throws SQLException {
		String sql = "INSERT INTO materia (nombre, codigo) VALUES (?, ?)";

		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, materia.getNombre());
			stmt.setLong(2, materia.getCodigo());

			return stmt.executeUpdate() > 0;
		}
	}

}
