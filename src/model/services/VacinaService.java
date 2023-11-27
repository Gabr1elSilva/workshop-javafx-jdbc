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

	public void saveOrUpdate(Vacina obj) {
		if (obj.getCodigo() == null) {
			dao.insert(obj);
		} else {
			dao.update(obj);
		}
	}
	
	public void deleteByCodigo(Long codigo) {
        dao.deleteByCodigo(codigo);
    }
}
