package model.dao;

import db.DB;
import model.dao.impl.PessoaDaoJDBC;
import model.dao.impl.VacinaDaoJDBC;

public class DaoFactory {

	public static PessoaDao createPessoaDao() {
		return new PessoaDaoJDBC(DB.getConnection());
	}
	
	public static VacinaDao createVacinaDao() {
		return new VacinaDaoJDBC(DB.getConnection());
	}
}
