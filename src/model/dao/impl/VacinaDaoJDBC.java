package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import db.DbIntegrityException;
import model.dao.VacinaDao;
import model.entities.Vacina;

public class VacinaDaoJDBC implements VacinaDao {

	private Connection conn;

	public VacinaDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public Vacina findByCodigo(Long codigo) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM vacina WHERE Codigo = ?");
			st.setLong(1, codigo);
			rs = st.executeQuery();

			if (rs.next()) {
				Vacina obj = new Vacina();
				obj.setCodigo(rs.getLong("Codigo"));
				obj.setNome(rs.getString("Nome"));
				obj.setDescricao(rs.getString("Descricao"));
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

	@Override
	public List<Vacina> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM vacina ORDER BY Nome");
			rs = st.executeQuery();

			List<Vacina> list = new ArrayList<>();

			while (rs.next()) {
				Vacina obj = new Vacina();
				obj.setCodigo(rs.getLong("Codigo"));
				obj.setNome(rs.getString("Nome"));
				obj.setDescricao(rs.getString("Descricao"));
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

	@Override
	public void insert(Vacina obj) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("INSERT INTO vacina (Nome, Descricao) VALUES (?, ?)",
	                Statement.RETURN_GENERATED_KEYS);

	        st.setString(1, obj.getNome());
	        st.setString(2, obj.getDescricao());

	        int rowsAffected = st.executeUpdate();

	        if (rowsAffected > 0) {
	            rs = st.getGeneratedKeys();
	            if (rs.next()) {
	                long codigo = rs.getLong(1);
	                obj.setCodigo(codigo);
	            }
			} else {
				throw new DbException("Failed to get generated key for Vacina.");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Vacina obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE vacina SET Nome = ?, Descricao = ? WHERE Codigo = ?");

			st.setString(1, obj.getNome());
			st.setString(2, obj.getDescricao());
			st.setLong(3, obj.getCodigo());

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
			st = conn.prepareStatement("DELETE FROM vacina WHERE Codigo = ?");

			st.setLong(1, codigo);

			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}
}
