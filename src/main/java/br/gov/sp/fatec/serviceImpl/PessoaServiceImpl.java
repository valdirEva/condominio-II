package br.gov.sp.fatec.serviceImpl;

import java.time.OffsetDateTime;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.sp.fatec.dto.ApartamentoDTO;
import br.gov.sp.fatec.dto.PessoaDTO;
import br.gov.sp.fatec.exceptionhandler.NegocioException;
import br.gov.sp.fatec.model.Apartamento;
import br.gov.sp.fatec.model.Pessoa;
import br.gov.sp.fatec.repository.ApartamentoRepository;
import br.gov.sp.fatec.repository.PessoaRepository;
import br.gov.sp.fatec.service.ApartamentoService;
import br.gov.sp.fatec.service.PessoaService;

@Service ("PessoaService")// componente do spring para colocar nossos serviços.
public class PessoaServiceImpl implements PessoaService{
	
	@Autowired // injeta a interface moradorRepository.
	private PessoaRepository pessoaRepository;
	

	@Autowired // injeta a interface moradorRepository.
	private ApartamentoRepository apartamentoRepository;
	
	@Autowired // injeta a interface moradorRepository.
	private ApartamentoService apartamentoService;

	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public List<Pessoa> Listar() {
		List<Pessoa> pessoas = pessoaRepository.findAll();
		if(pessoas.isEmpty())
		{
			throw new NegocioException(" Moradores não cadastrados");
		}
		List<Pessoa> pessoasretorno = new LinkedList<Pessoa>();
		for(Pessoa pessoa : pessoas)
		{
			Apartamento apartamento = pessoa.getApartamento();
			if(apartamento != null)
			{
				apartamento.setPessoas(null);
				apartamento.setVeiculos(null);
				pessoa.setApartamento(apartamento);
				pessoasretorno.add(pessoa);
			}
		}
		
		return pessoasretorno;
	}
	
	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public List<Pessoa>  buscarPessoaNome(String nome) {
		List<Pessoa> pessoas = pessoaRepository.findByNomeContains(nome);
		List<Pessoa> pessoasretorno = new LinkedList<Pessoa>();
		if (pessoas.isEmpty()) {
			
			throw new NegocioException(" Morador não encontrado.");
		}
		
		for(Pessoa pessoa : pessoas)
		{
			Apartamento apartamento = pessoa.getApartamento();
			if(apartamento != null)
			{
				apartamento.setPessoas(null);
				apartamento.setVeiculos(null);
				pessoa.setApartamento(apartamento);
				pessoasretorno.add(pessoa);
			}
		}
		
		
		return pessoasretorno;
		
	}
	
	
	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public Pessoa buscarPessoaRg(String rg) {
		Pessoa pessoa = pessoaRepository.findByrg(rg);
		if (pessoaRepository.findByrg(rg)== null) {
			
			throw new NegocioException(" Morador não cadastrado com este rg.");
		}
		
		Apartamento apartamento = pessoa.getApartamento();
		if(apartamento == null)
		{
			throw new NegocioException("Pessoa não esta cadastrada em um apartamento.");
		}
		apartamento.setPessoas(null);
		apartamento.setVeiculos(null);
		pessoa.setApartamento(apartamento);
		
		
		return pessoa 	;
		
	}
	
	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public List<Pessoa>  buscaNumeroApartamento(String numeroApartamento){
		
		Apartamento apartamento = apartamentoRepository.findByNumeroApartamento(Long.parseLong(numeroApartamento));
			List<Pessoa> pessoas = pessoaRepository.findByApartamento(apartamento);
			List<Pessoa> pessoasretorno = new LinkedList<Pessoa>();
			if (pessoas.isEmpty()) {
				
				throw new NegocioException(" Morador não encontrado.");
			}
			
			for(Pessoa pessoa : pessoas)
			{
				Apartamento apartamento2 = pessoa.getApartamento();
				if(apartamento2 != null)
				{
					apartamento2.setPessoas(null);
					apartamento2.setVeiculos(null);
					pessoa.setApartamento(apartamento2);
					pessoasretorno.add(pessoa);
				}
			}
			
			
			return pessoasretorno;
			
		}
	
	
	
	@Override
	@Transactional
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public Pessoa salvarPessoa(PessoaDTO pessoa) {
		Pessoa pessoa2 = new Pessoa();
		if (pessoaRepository.findByrg(pessoa.getRg())!= null) {
			
			throw new NegocioException("Já existe um morador cadastrado com este rg.");
		}
		if (apartamentoRepository.findByNumeroApartamento(pessoa.getNumeroApartamento())== null) {
			ApartamentoDTO apartamento = new ApartamentoDTO();
			apartamento.setNumeroApartamento(pessoa.getNumeroApartamento());
			apartamentoService.salvarApartamento(apartamento);
			pessoa.setNumeroApartamento(apartamento.getNumeroApartamento());
		}
		Apartamento apartamento= apartamentoRepository.findByNumeroApartamento(pessoa.getNumeroApartamento());
		pessoa2.setApartamento(apartamento);
		pessoa2.setNome(pessoa.getNome());
		pessoa2.setRg(pessoa.getRg());
		pessoa2.setDtNascimento(pessoa.getDtNascimento());
		pessoa2.setEmail(pessoa.getEmail());
		pessoa2.setCelular(pessoa.getCelular());
		pessoa2.setDtCriacao(OffsetDateTime.now());
		pessoaRepository.save(pessoa2);
		return pessoa2;
	}
	@Override
	@Transactional
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public Pessoa atualizaPessoa(Long pessoaId, PessoaDTO pessoa) {
		if (pessoaRepository.findById(pessoaId)== null) {
			
			throw new NegocioException("Morador não cadastrado.");
		}
		if (apartamentoRepository.findByNumeroApartamento(pessoa.getNumeroApartamento())== null) {
			ApartamentoDTO apartamento = new ApartamentoDTO();
			apartamento.setNumeroApartamento(pessoa.getNumeroApartamento());
			apartamentoService.salvarApartamento(apartamento);
			pessoa.setNumeroApartamento(apartamento.getNumeroApartamento());
		}
		Apartamento apartamento= apartamentoRepository.findByNumeroApartamento(pessoa.getNumeroApartamento());
		Pessoa pessoa2 =pessoaRepository.findById(pessoaId).get();
		pessoa2.setIdPesoa(pessoaId);
		pessoa2.setApartamento(apartamento);
		pessoa2.setNome(pessoa.getNome());
		pessoa2.setRg(pessoa.getRg());
		pessoa2.setDtNascimento(pessoa.getDtNascimento());
		pessoa2.setEmail(pessoa.getEmail());
		pessoa2.setCelular(pessoa.getCelular());
		pessoa2.setDtAtualizacao(OffsetDateTime.now());
		pessoaRepository.save(pessoa2);
		Apartamento apartamento2 = pessoa2.getApartamento();
		apartamento2.setPessoas(null);
		apartamento2.setVeiculos(null);
		pessoa2.setApartamento(apartamento2);
		return pessoa2;
	}
	
	@Override
	@Transactional
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public void excluir(long id) {
		if (pessoaRepository.findById(id) == null ) {
			throw new NegocioException("Pessoa  não enconotrada.");
		}
		pessoaRepository.deleteById(id);
		
	}


	
}
