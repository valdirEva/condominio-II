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
import br.gov.sp.fatec.model.Veiculo;
import br.gov.sp.fatec.repository.ApartamentoRepository;
import br.gov.sp.fatec.repository.VeiculoRepository;
import br.gov.sp.fatec.service.ApartamentoService;
import br.gov.sp.fatec.service.VeiculoService;

@Service ("VeiculoService")// componente do spring para colocar nossos serviços.
public class VeiculoServiceImpl implements VeiculoService{
	
	@Autowired // injeta a interface moradorRepository.
	private VeiculoRepository veiculoRepository;
	
	@Autowired // injeta a interface moradorRepository.
	private ApartamentoRepository apartamentoRepository;
	
	@Autowired // injeta a interface moradorRepository.
	private ApartamentoService apartamentoService;

	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public List<Veiculo> Listar() {
		List<Veiculo> veiculos = veiculoRepository.findAll();
		if (veiculos.isEmpty() == false)
		{
			for (Veiculo veiculo : veiculos)
			{
				Apartamento apartamento2 = veiculo.getApartamento();
				if(apartamento2 != null)
				{
					apartamento2.setPessoas(null);
					apartamento2.setVeiculos(null);
					veiculo.setApartamento(apartamento2);
				}
			}
		}
		else
			throw new NegocioException(" Não existem veículos cadastrados.");
		return veiculos;
	}
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public List<Veiculo>  buscaPorPlaca(String placaVeiculo){
		List<Veiculo> veiculos = veiculoRepository.findByPlacaContains(placaVeiculo);
		if (veiculos.isEmpty() == false)
		{
			for (Veiculo veiculo : veiculos)
			{
				Apartamento apartamento2 = veiculo.getApartamento();
				if(apartamento2 != null)
				{
					apartamento2.setPessoas(null);
					apartamento2.setVeiculos(null);
					veiculo.setApartamento(apartamento2);
				}
			}
		}
		else
			throw new NegocioException(" Não existem veículos cadastrados.");
		return veiculos;
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public List<Veiculo>  buscaPorAparatmento(Long ap){
		Apartamento apartamento = apartamentoRepository.findByNumeroApartamento(ap);
		List<Veiculo> veiculos = veiculoRepository.findByApartamento(apartamento);
		List<Veiculo> veiculosretorno = new LinkedList<Veiculo>();
		if (veiculos.isEmpty()) {
			
			throw new NegocioException(" Veículos não encontrados.");
		}
		
		for(Veiculo veiculo : veiculos)
		{
			Apartamento apartamento2 = veiculo.getApartamento();
			if(apartamento2 != null)
			{
				apartamento2.setPessoas(null);
				apartamento2.setVeiculos(null);
				veiculo.setApartamento(apartamento2);
				veiculosretorno.add(veiculo);
			}
		}
		
		
		return veiculosretorno;
		
	}

	
	
	
	@Override
	@Transactional
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public Veiculo salvarVeiculo(VeiculoDTO veiculo){
		Veiculo veiculo2 = new Veiculo();
		Apartamento apartamento = apartamentoRepository.findByNumeroApartamento(veiculo.getNumeroApartamento());
		if (apartamento == null) {
			ApartamentoDTO apartamento2 = new ApartamentoDTO();
			apartamento2.setNumeroApartamento(veiculo.getNumeroApartamento());
			apartamento = apartamentoService.salvarApartamento(apartamento2);
			veiculo.setNumeroApartamento(apartamento2.getNumeroApartamento());
		}
		
		if (veiculoRepository.findByPlaca(veiculo.getPlaca())!= null) {
			
			throw new NegocioException("Já existe um veiculo cadastrado com esta placa.");
		}
		
		veiculo2.setApartamento(apartamento);
		veiculo2.setPlaca(veiculo.getPlaca());
		veiculo2.setMarca(veiculo.getModelo());
		veiculo2.setModelo(veiculo.getModelo());
		veiculo2.setDtCriacao(OffsetDateTime.now());
		veiculoRepository.save(veiculo2);
		return veiculo2;
	}
	
	@Override
	@Transactional
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public Veiculo atualizaVeiculo(Long veiculoId, VeiculoDTO veiculo) {
		Veiculo veiculo2 =  veiculoRepository.findById(veiculoId).get();
		Apartamento apartamento = apartamentoRepository.findByNumeroApartamento(veiculo.getNumeroApartamento());
		if (apartamento == null) {
			ApartamentoDTO apartamento2 = new ApartamentoDTO();
			apartamento2.setNumeroApartamento(veiculo.getNumeroApartamento());
			apartamento = apartamentoService.salvarApartamento(apartamento2);
			veiculo.setNumeroApartamento(apartamento2.getNumeroApartamento());
		}
		
		if (veiculoRepository.findByPlaca(veiculo.getPlaca())!= null) {
			
			throw new NegocioException("Já existe um veiculo cadastrado com esta placa.");
		}
		veiculo2.setIdVeiculo(veiculoId);
		veiculo2.setApartamento(apartamento);
		veiculo2.setPlaca(veiculo.getPlaca());
		veiculo2.setMarca(veiculo.getModelo());
		veiculo2.setModelo(veiculo.getModelo());
		veiculo2.setDtAtualizacao(OffsetDateTime.now());
		veiculoRepository.save(veiculo2);
		return veiculo2;
		
	}


	@Override
	@Transactional
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public void excluir(Long id) {
		if (veiculoRepository.findById(id) == null ) {
			throw new NegocioException("Veiculo  não enconotrado.");
		}
		veiculoRepository.deleteById(id);
		
	}
	
}
