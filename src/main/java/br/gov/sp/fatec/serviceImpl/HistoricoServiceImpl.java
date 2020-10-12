package br.gov.sp.fatec.serviceImpl;

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
		return historicoRepository.findAll();
	}
	
	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public List<Historico> ListarPorTipoEDataEntrada(HistoricoDTO historico){
		return historicoRepository.findByTipoAndDtEntrada(historico.getTipo(), historico.getDtEntrada());
	}
	
	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public List<Historico> ListarPorTipo(HistoricoDTO historico){
		return historicoRepository.findByTipo(historico.getTipo());
	}
	
	@Override
	@Transactional
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public Historico salvarHistorico(HistoricoDTO historico) {
		Historico hitorico2 = new Historico();
		if (apartamentoRepository.findByNumeroApartamento(historico.getNumeroApartamento())== null) {
			
			throw new NegocioException("Numero de apartamento não encontrado.");
		}
		if (pessoaRepository.findByrg(historico.getRg())== null) {
				
				throw new NegocioException("Numero de rg não encontrado.");
		}
		Apartamento apartamento= apartamentoRepository.findByNumeroApartamento(historico.getNumeroApartamento());
		Pessoa pessoa= pessoaRepository.findByrg(historico.getRg());
		hitorico2.setPessoa(pessoa);
		hitorico2.setApartamento(apartamento);
		hitorico2.setTipo(historico.getTipo());
		hitorico2.setDtEntrada(dataHoraAtual);
		historicoRepository.save(hitorico2);
		return hitorico2;
	}
	@Override
	@Transactional
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public Historico atualizaHistorico(Long historicoId, HistoricoDTO historico) {
		if (historicoRepository.findById(historicoId)== null) {
			
			throw new NegocioException("Historico não encontrado.");
		}
		
		Historico hitorico2 = historicoRepository.findById(historicoId).get();
		
		if (apartamentoRepository.findByNumeroApartamento(historico.getNumeroApartamento())== null) {
			
			throw new NegocioException("Numero de apartamento não encontrado.");
		}
		if (pessoaRepository.findByrg(historico.getRg())== null) {
				
				throw new NegocioException("Numero de rg não encontrado.");
		}
		Apartamento apartamento= apartamentoRepository.findByNumeroApartamento(historico.getNumeroApartamento());
		Pessoa pessoa= pessoaRepository.findByrg(historico.getRg());
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
