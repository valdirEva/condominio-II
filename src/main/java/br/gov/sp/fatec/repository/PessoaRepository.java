package br.gov.sp.fatec.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.gov.sp.fatec.model.Pessoa;


@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

	public List<Pessoa> findByNome(String nome);
	
	Pessoa findByrg(String rg);
	
	Pessoa deleteByrg(String rg);
}
