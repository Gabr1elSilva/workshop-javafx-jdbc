package model.dao;

import java.time.LocalDate;
import java.util.List;

import model.entities.Pessoa;

public interface PessoaDao {
	
	List<Pessoa> findByParameters(Long codigo, String nome, String descricao, LocalDate dataInicial, LocalDate dataFinal);
	List<Pessoa> findAll();
}
