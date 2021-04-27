package br.gov.sp.fatec.model;



import java.time.OffsetDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import br.gov.sp.fatec.controller.View;





@Entity //Anotação da entidade
@Table(name = "pessoa")//Mapeamento da tabela.
public class Pessoa {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "idPessoa")
	@JsonView(View.Completo.class)
	private Long idPessoa;
	
	@JsonView(View.Resumo1.class)
	@ManyToOne (fetch = FetchType.EAGER)
	@JoinColumn(name = "idApartamento")
	private Apartamento apartamento;
	
	@Column(name = "nome", length = 20, nullable = false)
	@JsonView(View.Resumo1.class)
	private String nome;
	
	@Column(name = "rg ", unique = true, length = 30, nullable = false)
	@JsonView(View.Resumo1.class)
	private String rg;
	
	@Column(name = "dtNascimento", nullable = false)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@JsonView(View.Resumo1.class)
	private Date dtNascimento;
	
	@Email // informa que entrada tem que ser email.https://projects.eclipse.org/projects/ee4j.bean-validation
	@Size(max= 255) //informa  o tamanho maximo da entrada.https://projects.eclipse.org/projects/ee4j.bean-validation
	@JsonView(View.Completo.class)
	private String email;
	
	@Column(name = "celular")
	@JsonView(View.Completo.class)
	private Long celular;
	
	@Column(name = "dtCriacao", nullable = false)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@JsonProperty(access = Access.READ_ONLY)
	@JsonView(View.Completo.class)
	private  OffsetDateTime dtCriacao;
	
	@Column(name = "dtAtualizacao", nullable = false)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@JsonView(View.Completo.class)
	private  OffsetDateTime dtAtualizacao;
	
	

	public Long getIdPesoa() {
		return idPessoa;
	}

	public void setIdPesoa(Long idPesoa) {
		this.idPessoa = idPesoa;
	}

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

	public  OffsetDateTime getDtCriacao() {
		return dtCriacao;
	}

	public void setDtCriacao( OffsetDateTime dtCriacao) {
		this.dtCriacao = dtCriacao;
	}

	public  OffsetDateTime getDtAtualizacao() {
		return dtAtualizacao;
	}

	public void setDtAtualizacao( OffsetDateTime dtAtualizacao) {
		this.dtAtualizacao = dtAtualizacao;
	}

	public Long getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(Long idPessoa) {
		this.idPessoa = idPessoa;
	}

	public Apartamento getApartamento() {
		return apartamento;
	}

	public void setApartamento(Apartamento apartamento) {
		this.apartamento = apartamento;
	}

	
	
	
	
	
	

	
	
}

