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
import model.entities.Situacao;
import model.entities.Vacina;

public class VacinaDaoJDBC implements VacinaDao {

	private Connection conn;

	public VacinaDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	public void updateSituacao(Long codigo, Situacao situacao) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("UPDATE vacina SET situacao = ? WHERE codigo = ?");
            st.setString(1, situacao.name());
            st.setLong(2, codigo);

            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }
	
	public List<Vacina> findBySituacao(Situacao situacao) {
	    PreparedStatement st = null;
	    ResultSet rs = null;

	    try {
	        st = conn.prepareStatement("SELECT * FROM vacina WHERE situacao = ? AND situacao = 'ATIVO'");
	        st.setString(1, situacao.name());

	        rs = st.executeQuery();

	        List<Vacina> list = new ArrayList<>();

	        while (rs.next()) {
	            Long codigo = rs.getLong("codigo");
	            String nome = rs.getString("nome");
	            String descricao = rs.getString("descricao");
	            Situacao situacaoVacina = Situacao.valueOf(rs.getString("situacao"));

	            Vacina vacina = new Vacina(codigo, nome, descricao, situacaoVacina);
	            list.add(vacina);
	        }

	        return list;
	    } catch (SQLException e) {
	        throw new DbException(e.getMessage());
	    } finally {
	        DB.closeResultSet(rs);
	        DB.closeStatement(st);
	    }
	}

	@Override
	public List<Vacina> findByParameters(Long codigo, String nome, String descricao) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder("SELECT * FROM vacina WHERE 1=1");

			if (codigo != null) {
				sql.append(" AND codigo = ?");
			}
			if (nome != null) {
				sql.append(" AND UPPER(nome) like UPPER(?)");
			}
			if (descricao != null) {
				sql.append(" AND UPPER(descricao) like UPPER(?)");
			}

			st = conn.prepareStatement(sql.toString());

			int parameterIndex = 1;

			if (codigo != null) {
				st.setLong(parameterIndex++, codigo);
			}
			if (nome != null) {
				st.setString(parameterIndex++, "%" + nome + "%");
			}
			if (descricao != null) {
				st.setString(parameterIndex++, "%" + descricao + "%");
			}

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
		try {
			st = conn.prepareStatement("INSERT INTO vacina " + "(Nome, Descricao, situacao) " + "VALUES " + "(?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getNome());
			st.setString(2, obj.getDescricao());
			st.setString(3, Situacao.ATIVO.name());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					Long codigo = rs.getLong(1);
					obj.setCodigo(codigo);
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

	@Override
	public void update(Vacina obj) {
		PreparedStatement st = null;
		try {
			if (obj.getCodigo() == null || obj.getCodigo() == 0) {
				throw new DbException("O código da vacina não está definido.");
			}

			st = conn.prepareStatement("UPDATE vacina SET Nome = ?, Descricao = ? WHERE Codigo = ?");
			st.setString(1, obj.getNome());
			st.setString(2, obj.getDescricao());
			st.setLong(3, obj.getCodigo());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected == 0) {
				throw new DbException("Nenhuma vacina foi atualizada. Código não encontrado: " + obj.getCodigo());
			}
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
