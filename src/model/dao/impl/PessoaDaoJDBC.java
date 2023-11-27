package model.dao.impl;

import java.sql.Connection;
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
	public void insert(Pessoa obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO pessoa " + "(Codigo, Nome, Cpf, DataNascimento) " + "VALUES " + "(?, ?, ?, ?)",
					PreparedStatement.RETURN_GENERATED_KEYS);

			st.setLong(1, obj.getCodigo());
			st.setString(2, obj.getNome());
			st.setString(3, obj.getCpf());
			st.setObject(4, obj.getDataNascimento());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					Long codigo = rs.getLong(1);
					obj.setCodigo(codigo);
				}
				DB.closeResultSet(rs);
			} else {
				throw new DbException("Unexpected error! No rows affected!");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Pessoa obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE pessoa " + "SET Nome = ?, Cpf = ?, DataNascimento = ? " + "WHERE Codigo = ?");

			st.setString(1, obj.getNome());
			st.setString(2, obj.getCpf());
			st.setObject(3, obj.getDataNascimento());
			st.setLong(4, obj.getCodigo());

			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void deleteByCodigo(Long codigo) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM pessoa WHERE Codigo = ?");

			st.setLong(1, codigo);

			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Pessoa findByCodigo(Long codigo) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM pessoa WHERE Codigo = ?");
			st.setLong(1, codigo);

			rs = st.executeQuery();

			if (rs.next()) {
				Pessoa obj = instantiatePessoa(rs);
				return obj;
			}

			return null;
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

	private LocalDate getLocalDate(ResultSet rs, String dataNascimento) throws SQLException {
		java.sql.Date sqlDate = rs.getDate(dataNascimento);
		return (sqlDate != null) ? sqlDate.toLocalDate() : null;
	}

	@Override
	public List<Pessoa> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM pessoa");
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
}
