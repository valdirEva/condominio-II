package br.gov.sp.fatec.serviceImpl;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.sp.fatec.dto.UsuarioDTO;
import br.gov.sp.fatec.exceptionhandler.NegocioException;
import br.gov.sp.fatec.model.Autorizacao;
import br.gov.sp.fatec.model.Usuario;
import br.gov.sp.fatec.repository.AutorizacaoRepository;
import br.gov.sp.fatec.repository.UsuarioRepository;
import br.gov.sp.fatec.service.UsuarioService;

@Service("usuarioService")
public class UsuarioServiceImpl implements UsuarioService {
	@Autowired
	private AutorizacaoRepository autorizacaoRepo;
	@Autowired
	private UsuarioRepository usuarioRepo;
	@Autowired
	private PasswordEncoder passEncoder;
	
	
	
	// Lista todos usuarios
		@Override
		@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
		public List<Usuario> Listar() {
			List<Usuario> usuarios = usuarioRepo.findAll();
			List<Usuario> usuariosret = new LinkedList<Usuario>();
			if(usuarios.isEmpty())
			{
				throw new NegocioException(" Busca sem resultado");
			}
			for (Usuario usuario : usuarios) {
				usuario.setSenha("");
				usuariosret.add(usuario);
				
			}
			return usuariosret;
		}
		
		@Override
		@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
		public List<Usuario> buscarPorNomeOuEmail(String nome, String email)
		{
			List<Usuario> usuarios = usuarioRepo.findByNomeOrEmailContains(nome, email);
			List<Usuario> usuariosret = new LinkedList<Usuario>();
			if(usuarios.isEmpty())
			{
				throw new NegocioException(" Busca sem resultado");
			}
			for (Usuario usuario : usuarios) {
				usuario.setSenha("");
				usuariosret.add(usuario);
			
			}
			return usuariosret;
	}
	
	/*
	 * Sobreescrevendo método para buscar no repositorio usuario por nome ou email.
	 * Caso não encontre retorna usuario não encontrado.
	 */

	@Override 
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepo.findTop1ByNomeOrEmail(username, username);
		if (usuario == null) {
			throw new UsernameNotFoundException("Usuário " + username + " não encontrado");
		}
		return User.builder().username(username).password(usuario.getSenha())
				.authorities(usuario.getAutorizacoes().stream().map(Autorizacao::getNome).collect(Collectors.toList())
						.toArray(new String[usuario.getAutorizacoes().size()]))
				.build();
	}
	
	
	
	
	
	@Override
	@Transactional
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public Usuario novoUsuario(String nome, String email, String senha, String nomeAutorizacao) {
		if (usuarioRepo.findTop1ByNomeOrEmail(nome,email)!= null) {
			throw new NegocioException("Já existe um usuario cadastrado com este nome ou email.");
		}
		Autorizacao autorizacao = autorizacaoRepo.findByNome(nomeAutorizacao);
		if (autorizacao == null) {
			autorizacao = new Autorizacao();
			autorizacao.setNome(nomeAutorizacao);
			autorizacaoRepo.save(autorizacao);
		}
		
		Usuario usuario = new Usuario();
		usuario.setNome(nome);
		usuario.setEmail(email);
		usuario.setSenha(passEncoder.encode(senha));
		usuario.setAutorizacoes(new HashSet<Autorizacao>());
		usuario.getAutorizacoes().add(autorizacao);
		usuarioRepo.save(usuario);
		usuario.setSenha("");
		return usuario;
	}
	
	@Override
	@Transactional
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public Usuario editarUsuario(Long id, UsuarioDTO usuarioDto) {
		if (usuarioRepo.findById(id)== null) {
			throw new NegocioException("Usuario não existe.");
		}
		Autorizacao autorizacao = autorizacaoRepo.findByNome(usuarioDto.getAutorizacao());
		if (autorizacao == null) {
			autorizacao = new Autorizacao();
			autorizacao.setNome(usuarioDto.getAutorizacao());
			autorizacaoRepo.save(autorizacao);
		}
		
		Usuario usuario = new Usuario();
		usuario.setId(id);
		usuario.setNome(usuarioDto.getNome());
		usuario.setEmail(usuarioDto.getEmail());
		usuario.setSenha(passEncoder.encode(usuarioDto.getSenha()));
		usuario.setAutorizacoes(new HashSet<Autorizacao>());
		usuario.getAutorizacoes().add(autorizacao);
		usuarioRepo.save(usuario);
		usuario.setSenha("");
		return usuario;
	}
	
	
	
	
	
	
	
	// deleta usuario
	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public Usuario deleteUsuario(Long usuarioId) {
		
		usuarioRepo.deleteById(usuarioId);
		return null;
	}
}

