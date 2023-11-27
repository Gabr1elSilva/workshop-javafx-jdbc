package model.dao;

import java.util.List;

import model.entities.Pessoa;

public interface PessoaDao {
	
	void insert(Pessoa obj);
	void update(Pessoa obj);
	void deleteByCodigo(Long codigo);
	Pessoa findByCodigo(Long codigo);
	List<Pessoa> findAll();
}
