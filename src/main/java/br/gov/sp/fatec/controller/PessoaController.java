package br.gov.sp.fatec.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.fasterxml.jackson.annotation.JsonView;

import br.gov.sp.fatec.dto.PessoaDTO;
import br.gov.sp.fatec.model.Pessoa;
import br.gov.sp.fatec.service.PessoaService;

@RestController // anotacao para indicar ao spring que a classe é controller.
@CrossOrigin
@RequestMapping("/pessoas") // indica onde estao todas requisições.
public class PessoaController {

	//injeão de PessoaService
	@Autowired
	private PessoaService pessoaService;
	
	//Mapeamento para buscar todas pessoas.
	@GetMapping (value = "/all")
	@ResponseStatus(HttpStatus.OK)
	@JsonView(View.Completo.class)
	public List<Pessoa> Listar() {
		return pessoaService.Listar();
	}
	
	//Mapeamento para buscar um apartamento por Rg de um morador
	@GetMapping (value = "/nome/{nome}")
	@ResponseStatus(HttpStatus.OK)
	@JsonView(View.Completo.class)
	public List<Pessoa>  buscaNome(@PathVariable String nome){
		return pessoaService.buscarPessoaNome(nome);
	}
	
	//Mapeamento para buscar um apartamento por Rg de um morador
	@GetMapping (value = "/rg/{numeroRg}")
	@ResponseStatus(HttpStatus.OK)
	@JsonView(View.Completo.class)
	public Pessoa  buscaNumeroRg(@PathVariable String numeroRg){
		return pessoaService.buscarPessoaRg(numeroRg);
	}
	
	//Mapeamento para buscar uma lista de  apartamentos por Nome de um morador
	@GetMapping (value = "/ap/{numeroApartamento}")
	@ResponseStatus(HttpStatus.OK)
	@JsonView(View.Completo.class)
	public List<Pessoa> buscaNumeroApartamento(@PathVariable String numeroApartamento){
		return pessoaService.buscaNumeroApartamento(numeroApartamento);
	}
	
	//Mapeamento para criar pessoa
	@PostMapping(value = "/create")
	@ResponseStatus(HttpStatus.CREATED)
	@JsonView(View.Resumo.class)
	public Pessoa adicionar(@RequestBody @Valid PessoaDTO pessoa) {
		return pessoaService.salvarPessoa(pessoa);
	}
	
	//Mapeamento para atualizar uma pessoa
	@PutMapping("/put/{pessoaId}")
	@ResponseStatus(HttpStatus.OK)
	public Pessoa atualizaPessoa(@PathVariable Long pessoaId, @RequestBody @Valid PessoaDTO pessoa) {
		return pessoaService.atualizaPessoa(pessoaId, pessoa); 
	}
	
	//Mapeamento para deletar uma pessoa pelo RG.
	@DeleteMapping("/delete/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void excluir(@PathVariable long id) {
		
		pessoaService.excluir(id);
		
	}

}
