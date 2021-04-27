package br.gov.sp.fatec.model;

import java.time.OffsetDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonView;

import br.gov.sp.fatec.controller.View;

@Entity // Anotação para entidade.
@Table(name = "apartamento")//Mapeamento do nome da tabela
public class Apartamento {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "idApartamento")
	@JsonView(View.Completo.class)
	private Long idApartamento;
	
	
	@Column(name = "numeroApartamento",unique = true, length = 30, nullable = false)
	@JsonView(View.Resumo1.class)
	private Long numeroApartamento;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "apartamento")
	@JsonView(View.Resumo.class)
	private Set<Pessoa> pessoas;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "apartamento")
	@JsonView(View.Resumo.class)
	private Set<Veiculo> veiculos;

	@Column(name = "dtCriacao", nullable = false)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@JsonView(View.Completo.class)
	@JsonProperty(access = Access.READ_ONLY)
	private  OffsetDateTime dtCriacao;
	
	

	public Long getIdApartamento() {
		return idApartamento;
		}
	

	public void setIdApartamento(Long idApartamento) {
		this.idApartamento = idApartamento;
	}

	public Long getNumeroApartamento() {
		return numeroApartamento;
	}

	public void setNumeroApartamento(Long numeroApartamento) {
		this.numeroApartamento = numeroApartamento;
	}

	public OffsetDateTime getDtCriacao() {
		return dtCriacao;
	}

	public void setDtCriacao(OffsetDateTime dtCriacao) {
		this.dtCriacao = dtCriacao;
	}

	public Set<Veiculo> getVeiculos() {
		return veiculos;
	}

	public void setVeiculos(Set<Veiculo> veiculos) {
		this.veiculos = veiculos;
	}

	public Set<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(Set<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}
	
	

}
