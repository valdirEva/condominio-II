package br.gov.sp.fatec.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class PessoaDTO {
	
	private Long numeroApartamento;
	
	@NotBlank (message = "{name.not.blank}")
	@Size(min=3, max= 60)
	private String nome;
	
	@NotBlank (message = "{rg.not.blank}")
	private String rg;
	
	
	private Date dtNascimento;
	
	
	private String email;
	
	private Long celular;
	

	private String situacao;
	
	



	
	
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


	public Date getDtNascimento() {
		return dtNascimento;
	}


	public void setDtNascimento(Date dtNascimento) {
		this.dtNascimento = dtNascimento;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public Long getCelular() {
		return celular;
	}


	public void setCelular(Long celular) {
		this.celular = celular;
	}


	public String getSituacao() {
		return situacao;
	}


	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}


	public Long getNumeroApartamento() {
		return numeroApartamento;
	}


	public void setNumeroApartamento(Long numeroApartamento) {
		this.numeroApartamento = numeroApartamento;
	}


	


	
	
	
	

}
