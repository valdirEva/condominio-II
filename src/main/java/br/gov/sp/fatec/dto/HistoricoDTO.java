package br.gov.sp.fatec.dto;

import java.util.Date;

public class HistoricoDTO {
	
	private String nome;

	private String rg;
	
	private Long numeroApartamento;
	
	private String tipo;
	
	private Date dtEntrada;
	
	
	

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public Long getNumeroApartamento() {
		return numeroApartamento;
	}

	public void setNumeroApartamento(Long numeroApartamento) {
		this.numeroApartamento = numeroApartamento;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Date getDtEntrada() {
		return dtEntrada;
	}

	public void setDtEntrada(Date dtEntrada) {
		this.dtEntrada = dtEntrada;
	}

	
	
}
