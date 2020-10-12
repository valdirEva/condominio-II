package br.gov.sp.fatec.service;

import java.util.List;

import br.gov.sp.fatec.dto.PessoaDTO;
import br.gov.sp.fatec.model.Pessoa;
//interface 
public interface PessoaService {
	
	public List<Pessoa> Listar();
	
	public Pessoa buscarPessoaRg(String rg);
	
	public List<Pessoa>  buscarPessoaNome(String nome);
	
	public Pessoa salvarPessoa(PessoaDTO pessoa);
	
	public Pessoa atualizaPessoa(Long pessoaId, PessoaDTO pessoa);
	
	public void excluir(String rg);

}
