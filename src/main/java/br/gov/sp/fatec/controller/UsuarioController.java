package br.gov.sp.fatec.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.gov.sp.fatec.dto.UsuarioDTO;
import br.gov.sp.fatec.model.Usuario;
import br.gov.sp.fatec.service.UsuarioService;

@RestController
@RequestMapping(value = "/usuario")
@CrossOrigin
public class UsuarioController {
	@Autowired
	private UsuarioService usuarioService;
	
	
	// método para listar todos moradores
	@GetMapping
	public List<Usuario> Listar() {
		return usuarioService.Listar();
	}
	
	@PostMapping(path = "/nome")
    public List<Usuario> buscarPorNomeOuEmail(@RequestBody UsuarioDTO usuario)
    {
		return usuarioService.buscarPorNomeOuEmail(usuario.getNome(), usuario.getEmail());
    }
	
	//Método para cadastrar usuario
	@PostMapping(value = "/novo")
	public Usuario cadastrarUsuario(@RequestBody @Valid UsuarioDTO usuario) {
		return usuarioService.novoUsuario(usuario.getNome(), usuario.getEmail(), usuario.getSenha(),
				usuario.getAutorizacao());
	}
	
	//Mapeamento para atualizar  um usuario
		@PutMapping("put/{Id}")
		@ResponseStatus(HttpStatus.OK)
		public Usuario editarUsuario(@PathVariable Long Id, @RequestBody @Valid UsuarioDTO usuarioDto) {
			return usuarioService.editarUsuario(Id, usuarioDto); 
		}
	
	
	
	
	// metodo para deletar usuario
	@DeleteMapping("delete/{usuarioId}")
	public ResponseEntity<Void> removeUsuario(@PathVariable Long usuarioId) {
		
		usuarioService.deleteUsuario(usuarioId);
		return ResponseEntity.noContent().build();

	}
}
