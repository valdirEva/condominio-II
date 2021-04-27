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

import br.gov.sp.fatec.dto.VeiculoDTO;
import br.gov.sp.fatec.model.Veiculo;
import br.gov.sp.fatec.service.VeiculoService;

@RestController // anotacao para indicar ao spring que a classe é controller.
@CrossOrigin
@RequestMapping("/veiculos") // indica onde estao todas requisições.
public class VeiculoController {

	//Injeção da classeveiculo
	@Autowired
	private VeiculoService veiculoService;
	
	//Mapeamento para buscar todos veiculos
	@GetMapping (value = "/all")
	@ResponseStatus(HttpStatus.OK)
	@JsonView(View.Completo.class)
	public List<Veiculo> Listar() {
		return veiculoService.Listar();
	}
	
	@GetMapping (value = "/placa/{placaVeiculo}")
	@ResponseStatus(HttpStatus.OK)
	@JsonView(View.Completo.class)
	public List<Veiculo> buscaPorPlaca(@PathVariable String placaVeiculo) {
		return veiculoService.buscaPorPlaca(placaVeiculo);
	}
	
	@GetMapping (value = "/ap/{ap}")
	@ResponseStatus(HttpStatus.OK)
	@JsonView(View.Completo.class)
	public List<Veiculo> buscaPorPlaca(@PathVariable long ap) {
		return veiculoService.buscaPorAparatmento(ap);
	}
	
	//Mapeamento para criar um veiculo
	@PostMapping(value = "/create")
	@ResponseStatus(HttpStatus.CREATED)
	@JsonView(View.Completo.class)
	public Veiculo adicionar(@RequestBody @Valid VeiculoDTO veiculo) {
		return veiculoService.salvarVeiculo(veiculo);
	}

	//Mapeamento para atualizar um veiculo
	@PutMapping("put/{veiculoId}")
	@ResponseStatus(HttpStatus.OK)
	@JsonView(View.Completo.class)
	public Veiculo atualizaVeiculo(@PathVariable Long veiculoId, @RequestBody @Valid VeiculoDTO veiculo) {
		return veiculoService.atualizaVeiculo(veiculoId, veiculo); 
	}

	//Mapeamento paradeletar uma veiculo pela palca.
	@DeleteMapping("delete/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void excluir(@PathVariable Long id) {
		
		veiculoService.excluir(id);
		
	}
}
