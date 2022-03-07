package br.com.empresa.projeto.dto;

import java.io.Serializable;
import java.util.Objects;

public class FuncionarioDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String nome;
	private String matricula;

	public FuncionarioDto() {
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	@Override
	public int hashCode() {
		return Objects.hash(matricula, nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FuncionarioDto other = (FuncionarioDto) obj;
		return Objects.equals(matricula, other.matricula) && Objects.equals(nome, other.nome);
	}

	@Override
	public String toString() {
		return "FuncionarioDto [nome=" + nome + ", matricula=" + matricula + "]";
	}

}
