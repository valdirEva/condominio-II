
package br.gov.sp.fatec.service;

import java.util.List;

import br.gov.sp.fatec.dto.HistoricoDTO;
import br.gov.sp.fatec.model.Historico;
//interface 
public interface HistoricoService {
	
	public List<Historico> Listar();
	
	public List<Historico> ListarPorTipo(String tipo);
	
	public List<Historico> ListarPorTipoEDataEntrada(HistoricoDTO historico);
	
	public Historico salvarHistorico(HistoricoDTO historico);
	
	public List<Historico>  buscaNumeroApartamento(String numeroApartamento);
	
	public Historico atualizaHistorico(Long HistoricoId, HistoricoDTO historico);
	
	public void excluir(Long id);

}
