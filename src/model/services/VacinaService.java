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

	public void saveOrUpdate(Vacina obj) {
		if (obj.getCodigo() == null) {
			dao.insert(obj);
		}
		else {
			dao.update(obj);
		}
	}
	
	public void deleteByCodigo(Long codigo) {
		dao.deleteByCodigo(codigo);
	}

	public List<Vacina> findByParameters(Long codigo, String nome, String descricao) {
		return dao.findByParameters(codigo, nome, descricao);
	}
	
	public List<Vacina> findBySituacao(Situacao situacao){
		return dao.findBySituacao(situacao);
	}

	public void updateSituacao(Long codigo, Situacao situacao) {
		dao.updateSituacao(codigo, situacao);;
	}
}
