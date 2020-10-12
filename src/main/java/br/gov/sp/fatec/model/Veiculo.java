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
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import br.gov.sp.fatec.controller.View;

@Entity // Mapeamnto da entidade
@Table(name = "veiculo") //Mapeamento do nome da tabela
public class Veiculo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "idVeiculo")
	@JsonView(View.Completo.class)
	private Long idVeiculo;
	
	@ManyToOne (fetch = FetchType.EAGER)
	@JoinColumn(name = "idApartamento")
	
	private Apartamento apartamento;
	
	
	@Column(name = "placa ", unique = true, length = 30, nullable = false)
	@JsonView(View.Resumo.class)
	private String placa;
	
	@Column(name = "marca",  length = 30, nullable = false)
	@JsonView(View.Completo.class)
	private String marca;
	
	@Column(name = "modelo",  length = 30, nullable = false)
	@JsonView(View.Completo.class)
	private String modelo;
	
	
	@Column(name = "dtCriacao", nullable = false)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@JsonView(View.Completo.class)
	@JsonProperty(access = Access.READ_ONLY)
	private  OffsetDateTime dtCriacao;
	
	
	@Column(name = "dtAtualizacao", nullable = false)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@JsonView(View.Completo.class)
	@JsonProperty(access = Access.READ_ONLY)
	private  OffsetDateTime dtAtualizacao;

	public Long getIdVeiculo() {
		return idVeiculo;
	}

	public void setIdVeiculo(Long idVeiculo) {
		this.idVeiculo = idVeiculo;
	}

	
	

	public Apartamento getApartamento() {
		return apartamento;
	}

	public void setApartamento(Apartamento apartamento) {
		this.apartamento = apartamento;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	
	public OffsetDateTime getDtCriacao() {
		return dtCriacao;
	}

	public void setDtCriacao(OffsetDateTime dtCriacao) {
		this.dtCriacao = dtCriacao;
	}

	public OffsetDateTime getDtAtualizacao() {
		return dtAtualizacao;
	}

	public void setDtAtualizacao(OffsetDateTime dtAtualizacao) {
		this.dtAtualizacao = dtAtualizacao;
	}
	
	

	
	
	
}
