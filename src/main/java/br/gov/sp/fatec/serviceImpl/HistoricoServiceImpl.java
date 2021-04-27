package br.gov.sp.fatec.serviceImpl;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.sp.fatec.dto.HistoricoDTO;
import br.gov.sp.fatec.exceptionhandler.NegocioException;
import br.gov.sp.fatec.model.Apartamento;
import br.gov.sp.fatec.model.Historico;
import br.gov.sp.fatec.model.Pessoa;
import br.gov.sp.fatec.repository.ApartamentoRepository;
import br.gov.sp.fatec.repository.HistoricoRepository;
import br.gov.sp.fatec.repository.PessoaRepository;
import br.gov.sp.fatec.service.HistoricoService;

@Service ("HistoricoService")// componente do spring para colocar nossos serviços.
public class HistoricoServiceImpl implements HistoricoService{
	Date dataHoraAtual = new Date();
	
	@Autowired // injeta a interface moradorRepository.
	private HistoricoRepository historicoRepository;
	

	@Autowired // injeta a interface moradorRepository.
	private ApartamentoRepository apartamentoRepository;
	
	@Autowired // injeta a interface moradorRepository.
	private PessoaRepository pessoaRepository;

	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public List<Historico> Listar() {
		List<Historico> historicos = historicoRepository.findAll();
		if(historicos.isEmpty())
		{
			throw new NegocioException("Pesquisa sem resultado");
		}
		else
			return historicos;
	}
	
	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public List<Historico> ListarPorTipoEDataEntrada(HistoricoDTO historico){
		List<Historico> historicos =  historicoRepository.findByTipoAndDtEntrada(historico.getTipo(), historico.getDtEntrada());
		if(historicos.isEmpty())
		{
			throw new NegocioException("Pesquisa sem resultado");
		}
		else
			return historicos;
	}
	
	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public List<Historico> ListarPorTipo(String tipo){
		List<Historico> historicos = historicoRepository.findByTipo(tipo);
		if(historicos.isEmpty())
		{
			throw new NegocioException("Pesquisa sem resultado");
		}
		else
			return historicos;
	}
	
	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public List<Historico>  buscaNumeroApartamento(String numeroApartamento){
		
		Apartamento apartamento = apartamentoRepository.findByNumeroApartamento(Long.parseLong(numeroApartamento));
			List<Historico> historicos = historicoRepository.findByApartamento(apartamento);
			if (historicos.isEmpty()) {
				
				throw new NegocioException(" Apartamento sem histórico");
			}
			
			
			return historicos;
			
		}
	
	@Override
	@Transactional
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public Historico salvarHistorico(HistoricoDTO historico) {
		Historico hitorico2 = new Historico();
		Apartamento apartamento= apartamentoRepository.findByNumeroApartamento(historico.getNumeroApartamento());
		Pessoa pessoa= pessoaRepository.findByrg(historico.getRg());
		if (pessoa == null)
		{
			Pessoa pessoa2 = new Pessoa();
			pessoa2.setNome(historico.getNome());
			pessoa2.setRg(historico.getRg());
			pessoa2.setDtCriacao(OffsetDateTime.now());
			pessoa = pessoaRepository.save(pessoa2);
		}
		
		if (apartamento == null)
		{
			Apartamento apartamento2 = new Apartamento();
			apartamento2.setNumeroApartamento(historico.getNumeroApartamento());
			apartamento2.setDtCriacao(OffsetDateTime.now());
			apartamento = apartamentoRepository.save(apartamento2);
		}
		hitorico2.setPessoa(pessoa);
		hitorico2.setApartamento(apartamento);
		hitorico2.setTipo(historico.getTipo());
		hitorico2.setDtEntrada(OffsetDateTime.now());
		historicoRepository.save(hitorico2);
		return hitorico2;
	}
	@Override
	@Transactional
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public Historico atualizaHistorico(Long historicoId, HistoricoDTO historico) {
		
		
		Historico hitorico2 = historicoRepository.findById(historicoId).get();
		
		Apartamento apartamento= apartamentoRepository.findByNumeroApartamento(historico.getNumeroApartamento());
		Pessoa pessoa= pessoaRepository.findByrg(historico.getRg());
		if (pessoa == null)
		{
			Pessoa pessoa2 = new Pessoa();
			pessoa2.setNome(historico.getNome());
			pessoa2.setRg(historico.getRg());
			pessoa2.setDtCriacao(OffsetDateTime.now());
			pessoa = pessoaRepository.save(pessoa2);
		}
		else
		{
			
			pessoa.setNome(historico.getNome());
			pessoa.setRg(historico.getRg());
			pessoa.setDtCriacao(pessoa.getDtCriacao());
			pessoa.setDtAtualizacao(OffsetDateTime.now());
			pessoa = pessoaRepository.save(pessoa);
		}
		
		if (apartamento == null)
		{
			Apartamento apartamento2 = new Apartamento();
			apartamento2.setNumeroApartamento(historico.getNumeroApartamento());
			apartamento2.setDtCriacao(OffsetDateTime.now());
			apartamento = apartamentoRepository.save(apartamento2);
		}
		
		hitorico2.setIdHistorico(historicoId);
		hitorico2.setPessoa(pessoa);
		hitorico2.setApartamento(apartamento);
		hitorico2.setTipo(historico.getTipo());
		historicoRepository.save(hitorico2);
		return hitorico2;
	}

	
	@Override
	@Transactional
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public void excluir(Long id) {
		if (historicoRepository.findById(id) == null ) {
			throw new NegocioException("Historico   não enconotrado.");
		}
		historicoRepository.deleteById(id);
		
	}
	
}
