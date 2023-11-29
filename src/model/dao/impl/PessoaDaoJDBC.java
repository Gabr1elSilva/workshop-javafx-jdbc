package model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.PessoaDao;
import model.entities.Pessoa;

public class PessoaDaoJDBC implements PessoaDao {

	private Connection conn;

	public PessoaDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public List<Pessoa> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("SELECT * FROM pessoa ORDER BY Nome");
			rs = st.executeQuery();

			List<Pessoa> list = new ArrayList<>();

			while (rs.next()) {
				Pessoa obj = instantiatePessoa(rs);
				list.add(obj);
			}

			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private Pessoa instantiatePessoa(ResultSet rs) throws SQLException {
		Pessoa obj = new Pessoa();
		obj.setCodigo(rs.getLong("Codigo"));
		obj.setNome(rs.getString("Nome"));
		obj.setCpf(rs.getString("Cpf"));
		obj.setDataNascimento(getLocalDate(rs, "DataNascimento"));
		return obj;
	}

	private LocalDate getLocalDate(ResultSet rs, String columnName) throws SQLException {
		Date date = rs.getDate(columnName);
		return (date != null) ? date.toLocalDate() : null;
	}

	@Override
	public List<Pessoa> findByParameters(Long codigo, String nome, String cpf, LocalDate dataInicial,
			LocalDate dataFinal) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			StringBuilder queryBuilder = new StringBuilder("SELECT * FROM pessoa WHERE 1=1");

			if (codigo != null) {
				queryBuilder.append(" AND codigo = ?");
			}

			if (nome != null && !nome.isEmpty()) {
				queryBuilder.append(" AND UPPER(nome) LIKE UPPER(?)");
			}

			if (cpf != null && !cpf.isEmpty()) {
				queryBuilder.append(" AND UPPER(cpf) LIKE UPPER(?)");
			}

			if (dataInicial != null) {
				queryBuilder.append(" AND dataNascimento >= ?");
			}

			if (dataFinal != null) {
				queryBuilder.append(" AND dataNascimento <= ?");
			}

			st = conn.prepareStatement(queryBuilder.toString());

			int parameterIndex = 1;

			if (codigo != null) {
				st.setLong(parameterIndex++, codigo);
			}

			if (nome != null && !nome.isEmpty()) {
				st.setString(parameterIndex++, "%" + nome.toUpperCase() + "%");
			}

			if (cpf != null && !cpf.isEmpty()) {
				st.setString(parameterIndex++, "%" + cpf.toUpperCase() + "%");
			}

			if (dataInicial != null) {
				st.setDate(parameterIndex++, Date.valueOf(dataInicial));
			}

			if (dataFinal != null) {
				st.setDate(parameterIndex++, Date.valueOf(dataFinal));
			}

			rs = st.executeQuery();

			List<Pessoa> pessoas = new ArrayList<>();

			while (rs.next()) {
				Pessoa pessoa = instantiatePessoa(rs);
				pessoas.add(pessoa);
			}

			return pessoas;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
