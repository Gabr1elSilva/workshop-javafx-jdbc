package model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import db.DB;
import db.DbException;
import model.dao.AplicacaoDao;
import model.entities.Aplicacao;

public class AplicacaoDaoJDBC implements AplicacaoDao {

	private Connection conn;

	public AplicacaoDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Aplicacao aplicacao) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO aplicacao " + "(codigo_pessoa, codigo_vacina, data, situacao) "
					+ "VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

			st.setLong(1, aplicacao.getPessoa().getCodigo());
			st.setLong(2, aplicacao.getVacina().getCodigo());
			st.setDate(3, Date.valueOf(LocalDate.now())); // Insere a data atual
			st.setString(4, aplicacao.getSituacao().name());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					Long codigo = rs.getLong(1);
					aplicacao.setCodigo(codigo);
				}
			} else {
				throw new DbException("Unexpected error! No rows affected!");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

}
