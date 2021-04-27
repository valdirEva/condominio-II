package br.gov.sp.fatec.service;

import java.util.List;

import br.gov.sp.fatec.dto.ApartamentoDTO;
import br.gov.sp.fatec.dto.VeiculoDTO;
import br.gov.sp.fatec.model.Apartamento;
//interface 
public interface ApartamentoService {
	
	public List<Apartamento> Listar();
	
	public Apartamento buscaNumeroApartamento(Long numeroApartamento);
	
	public List<ApartamentoDTO> buscaNome(String nome);
	
	public VeiculoDTO buscaPlaca(String placa);
	
	public Apartamento salvarApartamento(ApartamentoDTO apartamento);
	
	public Apartamento atualizaApartamento(Long apartamentoId, ApartamentoDTO apartamento);
	
	public void excluirNumero(Long numeroAp);
	
	

}
