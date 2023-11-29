package model.services;

import model.dao.AplicacaoDao;
import model.dao.DaoFactory;
import model.entities.Aplicacao;

public class AplicacaoService {

	private AplicacaoDao dao = DaoFactory.createAplicacaoDao();

	public void insert(Aplicacao obj) {
		dao.insert(obj);
	}

}
