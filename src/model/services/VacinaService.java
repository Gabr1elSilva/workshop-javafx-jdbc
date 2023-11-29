package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.VacinaDao;
import model.entities.Situacao;
import model.entities.Vacina;

public class VacinaService {

	private VacinaDao dao = DaoFactory.createVacinaDao();

	public List<Vacina> findAll() {
		return dao.findAll();
	}

	public void insert(Vacina obj) {
		dao.insert(obj);
	}

	public void update(Vacina obj) {
		dao.insert(obj);
	}

	public void deleteByCodigo(Long codigo) {
		dao.deleteByCodigo(codigo);
	}

	public List<Vacina> findByParameters(Long codigo, String nome, String descricao) {
		return dao.findByParameters(codigo, nome, descricao);
	}

	public List<Vacina> findBySituacao(Situacao situacao) {
		return dao.findBySituacao(situacao);
	}

	public void updateSituacao(Long codigo, Situacao situacao) {
		dao.updateSituacao(codigo, situacao);
		;
	}
}
