package br.gov.sp.fatec.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.gov.sp.fatec.model.Apartamento;
import br.gov.sp.fatec.model.Historico;



@Repository
public interface HistoricoRepository extends JpaRepository<Historico, Long>{
	
	List <Historico> findByTipo(String tipo);
	List <Historico> findByTipoAndDtEntrada(String tipo, Date dtEntrada);
	public List<Historico> findByApartamento(Apartamento apartamento);
	
	Historico findByPessoa(String rg);
	
	
	
}