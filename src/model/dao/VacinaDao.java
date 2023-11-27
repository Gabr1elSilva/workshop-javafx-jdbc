package model.dao;

import java.util.List;

import model.entities.Vacina;

public interface VacinaDao {

	void insert(Vacina obj);
	void update(Vacina obj);
	void deleteByCodigo(Long codigo);
	Vacina findByCodigo(Long codigo);
	List<Vacina> findAll();
}
