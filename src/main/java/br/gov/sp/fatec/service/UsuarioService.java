package br.gov.sp.fatec.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import br.gov.sp.fatec.dto.UsuarioDTO;
import br.gov.sp.fatec.model.Usuario;

public interface UsuarioService extends UserDetailsService{
	
	public Usuario novoUsuario(String nome, String email, String senha, String nomeAutorizacao);
	
	public Usuario editarUsuario(Long id, UsuarioDTO usuarioDto);
	
	
	public List<Usuario> Listar();
	

	public Usuario deleteUsuario(Long usuarioId);
}

