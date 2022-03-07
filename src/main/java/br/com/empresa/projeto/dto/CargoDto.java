package br.com.empresa.projeto.dto;

import java.io.Serializable;
import java.util.Objects;

public class CargoDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nome;

	public CargoDto() {
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public int hashCode() {
		return Objects.hash(nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CargoDto other = (CargoDto) obj;
		return Objects.equals(nome, other.nome);
	}

	@Override
	public String toString() {
		return "CargoDto [nome=" + nome + "]";
	}

}
