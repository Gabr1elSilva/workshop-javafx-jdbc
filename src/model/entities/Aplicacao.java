package model.entities;

import java.time.LocalDate;
import java.util.Objects;

public class Aplicacao {
	private Long codigo;
	private LocalDate data;
	private Pessoa pessoa;
	private Vacina vacina;
	private Situacao situacao;

	public Aplicacao() {
	}

	public Aplicacao(Long codigo, LocalDate data, Pessoa pessoa, Vacina vacina, Situacao situacao) {
		this.codigo = codigo;
		this.data = data;
		this.pessoa = pessoa;
		this.vacina = vacina;
		this.situacao = situacao;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Vacina getVacina() {
		return vacina;
	}

	public void setVacina(Vacina vacina) {
		this.vacina = vacina;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	@Override
	public String toString() {
		return "Aplicacao [codigo=" + codigo + ", data=" + data + ", pessoa=" + pessoa + ", vacina=" + vacina
				+ ", situacao=" + situacao + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigo, data, pessoa, situacao, vacina);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Aplicacao other = (Aplicacao) obj;
		return Objects.equals(codigo, other.codigo) && Objects.equals(data, other.data)
				&& Objects.equals(pessoa, other.pessoa) && situacao == other.situacao
				&& Objects.equals(vacina, other.vacina);
	}
}
