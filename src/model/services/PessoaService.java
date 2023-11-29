package model.services;

import java.time.LocalDate;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.PessoaDao;
import model.entities.Pessoa;

public class PessoaService {

	private PessoaDao dao = DaoFactory.createPessoaDao();

	public List<Pessoa> findAll() {
		return dao.findAll();
	}

	public List<Pessoa> findByParameters(Long codigo, String nome, String descricao, LocalDate dataInicial, LocalDate dataFinal) {
		return dao.findByParameters(codigo, nome, descricao, dataInicial, dataFinal);
	}

}
