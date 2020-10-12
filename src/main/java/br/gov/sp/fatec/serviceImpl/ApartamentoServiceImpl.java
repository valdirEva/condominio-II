package br.gov.sp.fatec.serviceImpl;

import java.time.OffsetDateTime;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.sp.fatec.dto.ApartamentoDTO;
import br.gov.sp.fatec.dto.VeiculoDTO;
import br.gov.sp.fatec.exceptionhandler.NegocioException;
import br.gov.sp.fatec.model.Apartamento;
import br.gov.sp.fatec.model.Pessoa;
import br.gov.sp.fatec.model.Veiculo;
import br.gov.sp.fatec.repository.ApartamentoRepository;
import br.gov.sp.fatec.repository.VeiculoRepository;
import br.gov.sp.fatec.service.ApartamentoService;
import br.gov.sp.fatec.service.PessoaService;

@Service ("ApartamentoService")// componente do spring para colocar nossos serviços.
public class ApartamentoServiceImpl implements ApartamentoService{
	
	@Autowired // injeta a interface apartamentoRepository.
	private ApartamentoRepository apartamentoRepository;
	
	@Autowired // injeta a interface pessoaService.
	private PessoaService pessoaService;
	
	@Autowired // injeta a interface veiculoRepository.
	private VeiculoRepository veiculoRepository;

	
	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public List<Apartamento> Listar() {
		return apartamentoRepository.findAll();
	}
	
	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public Apartamento buscaNumeroApartamento(Long numeroApartamento){
		
		return apartamentoRepository.findByNumeroApartamento(numeroApartamento);
	}
	
	@Override
	@Transactional
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public List<ApartamentoDTO> buscaNome(String nome){
		List<Pessoa> pessoas = pessoaService.buscarPessoaNome(nome);
		List<ApartamentoDTO> apartamentos = new LinkedList<ApartamentoDTO>();
		for(Pessoa pessoa: pessoas ) {
			ApartamentoDTO apartamento = new ApartamentoDTO();
			apartamento.setNumeroApartamento(buscaNumeroRg(pessoa.getRg()).getNumeroApartamento());
			apartamento.setNome(pessoa.getNome());
			apartamento.setRg(pessoa.getRg());
			apartamentos.add(apartamento);
		}
		return apartamentos;
	}
	
	@Override
	@Transactional
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public VeiculoDTO buscaPlaca(String placa){
		Veiculo veiculo = veiculoRepository.findByPlaca(placa);
		if (veiculo ==null) {
			throw new NegocioException("Veículo não localizado.");
		}
		VeiculoDTO veiculoDTO = new VeiculoDTO();
		veiculoDTO.setNumeroApartamento(veiculo.getApartamento().getNumeroApartamento());
		veiculoDTO.setPlaca(veiculo.getPlaca());
		veiculoDTO.setMarca(veiculo.getMarca());
		veiculoDTO.setModelo(veiculo.getModelo());
		return veiculoDTO;
	}
	
	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public Apartamento buscaNumeroRg(String numeroRg){
		Pessoa pessoa = pessoaService.buscarPessoaRg(numeroRg);
		return apartamentoRepository.findByNumeroApartamento(pessoa.getApartamento().getNumeroApartamento());
	}
	
	@Override
	@Transactional
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public Apartamento salvarApartamento(ApartamentoDTO apartamento){
		Apartamento apartamento2 = new Apartamento();
			if (apartamentoRepository.findByNumeroApartamento(apartamento.getNumeroApartamento())!= null) {
			
			throw new NegocioException("Já existe um apartamento cadastrado com este numro.");
		}
		
		apartamento2.setNumeroApartamento(apartamento.getNumeroApartamento());
		apartamento2.setDtCriacao(OffsetDateTime.now());
		
		apartamentoRepository.save(apartamento2);
		return apartamento2;
	}
	@Override
	@Transactional
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public Apartamento atualizaApartamento(Long apartamentoId, ApartamentoDTO apartamento) {
			if (apartamentoRepository.findById(apartamentoId)== null) {
			
			throw new NegocioException("Apartamento não cadastrado.");
		}
		Apartamento apartamento2 =apartamentoRepository.findById(apartamentoId).get();
		apartamento2.setIdApartamento(apartamentoId);
		apartamento2.setNumeroApartamento(apartamento.getNumeroApartamento());
		apartamentoRepository.save(apartamento2);
		return apartamento2;
	}
	
	@Override
	@Transactional
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public void excluirNumero(Long numeroAp) {
		if (buscaNumeroApartamento(numeroAp) == null ) {
			throw new NegocioException("Apartamento não enconotrado.");
		}
		apartamentoRepository.deleteById(buscaNumeroApartamento(numeroAp).getIdApartamento());
		
	}

	
}
