package model.services;

import java.util.ArrayList;
import java.util.List;

import model.entities.Vacina;

public class VacinaService {

	public List<Vacina> findAll() {
		List<Vacina> list = new ArrayList<Vacina>();
		list.add(new Vacina(1L, "Pfizer", "Jair"));
		list.add(new Vacina(2L, "Gripe", "Messias"));
		list.add(new Vacina(3L, "Covid", "Bolsonaro"));
		return list;
	}
}
