package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.VacinaDao;
import model.entities.Vacina;

public class VacinaService {
	
	private VacinaDao dao = DaoFactory.createVacinaDao();

	public List<Vacina> findAll() {
		return dao.findAll();
	}
}
