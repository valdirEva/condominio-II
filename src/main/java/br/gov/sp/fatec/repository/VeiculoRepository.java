package br.gov.sp.fatec.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.gov.sp.fatec.model.Veiculo;



@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long>{
	
	Veiculo deleteByPlaca(String veiculoPlaca);
	
	Veiculo findByPlaca(String veiculoPlaca);
	
	List <Veiculo> findByMarcaOrModelo(String marca, String modelo);
	
	
	
}