package br.gov.sp.fatec.model;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonView;

import br.gov.sp.fatec.controller.View;

@Entity // Anotação da entidade
@Table(name = "historico")//MApeamnto do nome da tabela
public class Historico {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "idHistorico")
	@JsonView(View.Resumo1.class)
	private Long idHistorico;
	
	@ManyToOne (fetch = FetchType.EAGER)
	@JoinColumn(name = "idPessoa")
	@JsonView(View.Resumo1.class)
	private Pessoa pessoa;
	
	@ManyToOne (fetch = FetchType.EAGER)
	@JoinColumn(name = "idApartamento")
	@JsonView(View.Resumo1.class)
	private Apartamento apartamento;
	
	@Column(name = "tipo", unique = true, length = 30, nullable = false)
	@JsonView(View.Resumo1.class)
	private String tipo;
	
	@Column(name = "dtEntrada", nullable = false)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@JsonProperty(access = Access.READ_ONLY)
	@JsonView(View.Resumo1.class)
	private  OffsetDateTime dtEntrada;

	public Long getIdHistorico() {
		return idHistorico;
	}

	public void setIdHistorico(Long idHistorico) {
		this.idHistorico = idHistorico;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Apartamento getApartamento() {
		return apartamento;
	}

	public void setApartamento(Apartamento apartamento) {
		this.apartamento = apartamento;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public OffsetDateTime getDtEntrada() {
		return dtEntrada;
	}

	public void setDtEntrada(OffsetDateTime offsetDateTime) {
		this.dtEntrada = offsetDateTime;
	}
	
	

}
