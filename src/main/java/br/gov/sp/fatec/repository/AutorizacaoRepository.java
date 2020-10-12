package br.gov.sp.fatec.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.sp.fatec.model.Autorizacao;

public interface AutorizacaoRepository extends JpaRepository<Autorizacao, Long> {
	public Autorizacao findByNome(String nome);
}