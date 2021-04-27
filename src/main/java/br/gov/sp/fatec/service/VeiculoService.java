package br.gov.sp.fatec.service;

import java.util.List;

import br.gov.sp.fatec.dto.VeiculoDTO;
import br.gov.sp.fatec.model.Veiculo;
//interface 
public interface VeiculoService {
	
	public List<Veiculo> Listar();
	
	public Veiculo salvarVeiculo(VeiculoDTO veiciulo);
	
	public List<Veiculo>  buscaPorPlaca(String placaVeiculo);
	
	public List<Veiculo>  buscaPorAparatmento(Long ap);
	
	public Veiculo atualizaVeiculo(Long veiculoId, VeiculoDTO veiculo);
	
	public void excluir(Long id);

}
