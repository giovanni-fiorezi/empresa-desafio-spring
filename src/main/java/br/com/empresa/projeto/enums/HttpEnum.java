package br.com.empresa.projeto.enums;

public enum HttpEnum {
	
	HEADER_MESSAGE("mensagem");
	
	private String descricao;
	
	private HttpEnum(String descricao) {
		this.descricao = descricao;
	}
	
	@Override
	public String toString() {
		return this.descricao;
	}
}
