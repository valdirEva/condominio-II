package br.gov.sp.fatec.dto;

public class ApartamentoDTO {
	
	
	private Long numeroApartamento;
	
	private String nome;
	
	private String Rg;
	
	
	
	

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRg() {
		return Rg;
	}

	public void setRg(String rg) {
		Rg = rg;
	}

	public Long getNumeroApartamento() {
		return numeroApartamento;
	}

	public void setNumeroApartamento(Long numeroApartamento) {
		this.numeroApartamento = numeroApartamento;
	}
		

}
