package br.gov.sp.fatec.serviceImpl;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.sp.fatec.dto.PessoaDTO;
import br.gov.sp.fatec.exceptionhandler.NegocioException;
import br.gov.sp.fatec.model.Apartamento;
import br.gov.sp.fatec.model.Pessoa;
import br.gov.sp.fatec.repository.ApartamentoRepository;
import br.gov.sp.fatec.repository.PessoaRepository;
import br.gov.sp.fatec.service.PessoaService;

@Service ("PessoaService")// componente do spring para colocar nossos serviços.
public class PessoaServiceImpl implements PessoaService{
	
	@Autowired // injeta a interface moradorRepository.
	private PessoaRepository pessoaRepository;
	

	@Autowired // injeta a interface moradorRepository.
	private ApartamentoRepository apartamentoRepository;

	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public List<Pessoa> Listar() {
		return pessoaRepository.findAll();
	}
	
	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public List<Pessoa>  buscarPessoaNome(String nome) {
		if (pessoaRepository.findByNome(nome)== null) {
			
			throw new NegocioException(" Morador não encontrado.");
		}
		
		return pessoaRepository.findByNome(nome);
		
	}
	
	
	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public Pessoa buscarPessoaRg(String rg) {
		if (pessoaRepository.findByrg(rg)== null) {
			
			throw new NegocioException(" Morador não cadastrado com este rg.");
		}
		
		return pessoaRepository.findByrg(rg);
		
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
				
				throw new NegocioException("Apartamento não cadastrado.");
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
				
				throw new NegocioException("Apartamento não cadastrado.");
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
		return pessoa2;
	}
	
	@Override
	@Transactional
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public void excluir(String rg) {
		if (buscarPessoaRg(rg) == null ) {
			throw new NegocioException("Pessoa  não enconotrada.");
		}
		pessoaRepository.deleteById(buscarPessoaRg(rg).getIdPesoa());
		
	}


	
}
