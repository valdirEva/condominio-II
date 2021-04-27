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

import br.gov.sp.fatec.dto.HistoricoDTO;
import br.gov.sp.fatec.model.Historico;
import br.gov.sp.fatec.service.HistoricoService;

@RestController // anotacao para indicar ao spring que a classe é controller.
@CrossOrigin
@RequestMapping("/historicos") // indica onde estao todas requisições.
public class HistoricoController {

	//injeção de HistoricoService.
	@Autowired
	private HistoricoService historicoService;
	
	//Mapeamento para retornar todos historicos.
	@GetMapping (value = "/all")
	@ResponseStatus(HttpStatus.OK)
	@JsonView(View.Resumo1.class)
	public List<Historico> Listar() {
		return historicoService.Listar();
	}
	
	//Mapeamento para buscar uma lista de  apartamentos por Nome de um morador
	@GetMapping (value = "/ap/{numeroApartamento}")
	@ResponseStatus(HttpStatus.OK)
	@JsonView(View.Resumo1.class)
	public List<Historico> buscaNumeroApartamento(@PathVariable String numeroApartamento){
		return historicoService.buscaNumeroApartamento(numeroApartamento);
	}
	
	//Mapeamento para retornar todos historicos por um tipo e data
	@PostMapping (value = "/td")
	@ResponseStatus(HttpStatus.OK)
	@JsonView(View.Resumo1.class)
	public List<Historico> ListarPorTipoEDataEntrada(@RequestBody HistoricoDTO historico){
		return historicoService.ListarPorTipoEDataEntrada(historico);
	}
	
	//Mapeamento para retornar todos historicos por um tipo 
	@GetMapping (value = "/tipo/{tipo}")
	@ResponseStatus(HttpStatus.OK)
	@JsonView(View.Resumo1.class)
	public List<Historico> ListarPorTipo(@PathVariable String tipo){
		return historicoService.ListarPorTipo(tipo);
	}
	
	//Mapeamento para criar um historico
	@PostMapping(value = "/create")
	@ResponseStatus(HttpStatus.CREATED)
	@JsonView(View.Resumo1.class)
	public Historico adicionar(@RequestBody @Valid HistoricoDTO histoico) {
		return historicoService.salvarHistorico(histoico);
	}
	
	//Mapeamento para atualizar  um historico
	@PutMapping("put/{historicoId}")
	@ResponseStatus(HttpStatus.OK)
	@JsonView(View.Resumo1.class)
	public Historico atualizaHistorico(@PathVariable Long historicoId, @RequestBody @Valid HistoricoDTO histoico) {
		return historicoService.atualizaHistorico(historicoId, histoico); 
	}

	//Mapeamento para deletar  um historico
	@DeleteMapping("delete/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void excluir(@PathVariable Long id) {
		
		historicoService.excluir(id);
		
	}
}
