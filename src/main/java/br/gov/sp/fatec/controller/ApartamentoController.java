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

import br.gov.sp.fatec.dto.ApartamentoDTO;
import br.gov.sp.fatec.dto.VeiculoDTO;
import br.gov.sp.fatec.model.Apartamento;
import br.gov.sp.fatec.service.ApartamentoService;

@RestController // anotacao para indicar ao spring que a classe é controller.
@CrossOrigin
@RequestMapping("/apartamentos") // indica onde estao todas requisições.
public class ApartamentoController {

	//injeção de apartamentoService
	@Autowired
	private ApartamentoService apartamentoService;
	
	//Mapeamento para retornar todos apartamentos
	@GetMapping (value = "/all")
	@ResponseStatus(HttpStatus.OK)
	@JsonView(View.Resumo.class)
	public List<Apartamento> Listar() {
		return apartamentoService.Listar();
	}
	
	//Mapeamento para buscar um apartamento por numero
	@GetMapping (value = "/ap/{numeroApartamento}")
	@ResponseStatus(HttpStatus.OK)
	@JsonView(View.Resumo.class)
	public Apartamento buscaNumeroApartamento(@PathVariable Long numeroApartamento){
		return apartamentoService.buscaNumeroApartamento(numeroApartamento);
	}
	
	//Mapeamento para buscar um apartamento por Rg de um morador
	@GetMapping (value = "/rg/{numeroRg}")
	@ResponseStatus(HttpStatus.OK)
	@JsonView(View.Resumo.class)
	public Apartamento  buscaNumeroRg(@PathVariable String numeroRg){
		return apartamentoService.buscaNumeroRg(numeroRg);
	}
	
	//Mapeamento para buscar uma lista de  apartamentos por Nome de um morador
	@GetMapping (value = "/nome/{nome}")
	@ResponseStatus(HttpStatus.OK)
	public List<ApartamentoDTO> buscaNome(@PathVariable String nome){
		return apartamentoService.buscaNome(nome);
	}
	
	//Mapeamento para buscar um apartamento por placa de um veículo
	@GetMapping (value = "/placa/{placa}")
	@ResponseStatus(HttpStatus.OK)
	public  VeiculoDTO buscaPlaca(@PathVariable String placa){
		return apartamentoService.buscaPlaca(placa);
	}
	
	//Mapeamento para criar um apartamento
	@PostMapping(value = "/create")
	@ResponseStatus(HttpStatus.CREATED)
	@JsonView(View.Resumo.class)
	public Apartamento adicionar(@RequestBody @Valid ApartamentoDTO apartamento) {
		return apartamentoService.salvarApartamento(apartamento);
	}

	//Mapeamento para atualizar  um apartamento 
	@PutMapping("put/{apartamentoId}")
	@ResponseStatus(HttpStatus.OK)
	public Apartamento atualizaApartamento(@PathVariable Long apartamentoId, @RequestBody @Valid ApartamentoDTO apartamento) {
		return apartamentoService.atualizaApartamento(apartamentoId, apartamento); 
	}

	//Mapeamento para deletar  um apartamento passando o numero do mesmo.
	@DeleteMapping("delete/{numeroAp}")
	@ResponseStatus(HttpStatus.OK)
	public void excluirNumero(@PathVariable Long numeroAp) {
		
		apartamentoService.excluirNumero(numeroAp);
		
	}
}
