package model.entities;

import java.time.LocalDate;
import java.util.Objects;

public class Pessoa {
	private Long codigo;
	private String nome;
	private String cpf;
	private LocalDate dataNascimento;
	private Situacao situacao;

	public Pessoa() {
		codigo = -1L;
		nome = "Sem nome";
		cpf = "Sem CPF";
		dataNascimento = LocalDate.now();
		situacao = situacao.ATIVO;
	}

	public Pessoa(Long codigo, String nome, String cpf, LocalDate dataNascimento, Situacao situacao) {
		this.codigo = codigo;
		this.nome = nome;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
		this.situacao = situacao;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	@Override
	public String toString() {
		return "Pessoa [codigo=" + codigo + ", nome=" + nome + ", cpf=" + cpf + ", dataNascimento=" + dataNascimento
				+ ", situacao=" + situacao + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigo, cpf, dataNascimento, nome, situacao);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pessoa other = (Pessoa) obj;
		return Objects.equals(codigo, other.codigo) && Objects.equals(cpf, other.cpf)
				&& Objects.equals(dataNascimento, other.dataNascimento) && Objects.equals(nome, other.nome)
				&& situacao == other.situacao;
	}

}
