package br.gov.sp.fatec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.gov.sp.fatec.model.Apartamento;

@Repository
public interface ApartamentoRepository extends JpaRepository<Apartamento, Long>{ 
	

	
	Apartamento findByNumeroApartamento(Long numeroApartamento);
	
	

}
