package br.gov.sp.fatec.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.sp.fatec.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	public Usuario findTop1ByNomeOrEmail(String nome, String email);
}
