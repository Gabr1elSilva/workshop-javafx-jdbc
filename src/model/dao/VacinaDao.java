package model.dao;

import java.util.List;

import model.entities.Situacao;
import model.entities.Vacina;

public interface VacinaDao {

	void insert(Vacina obj);

	void update(Vacina obj);

	void deleteByCodigo(Long codigo);

	List<Vacina> findByParameters(Long codigo, String nome, String descricao);

	List<Vacina> findAll();

	List<Vacina> findBySituacao(Situacao situacao);

	void updateSituacao(Long codigo, Situacao situacao);
}
