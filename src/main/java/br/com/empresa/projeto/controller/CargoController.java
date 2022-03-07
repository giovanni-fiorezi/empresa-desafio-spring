package br.com.empresa.projeto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.empresa.projeto.dto.CargoDto;
import br.com.empresa.projeto.service.CargoService;

@RestController
@RequestMapping("/cargo")
public class CargoController {
	
	@Autowired
	private CargoService service;
	
	@GetMapping
	public List<CargoDto> buscaTodosOsCargos(){
		return service.buscaTodosOsCargos();
	}
	
	@GetMapping("/{nome}")
	public ResponseEntity<CargoDto> buscaCargoPorNome(@PathVariable String nome) {
		try {
			return ResponseEntity.ok(service.buscaCargoPorNome(nome));
		}catch (Exception ex) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping
	public ResponseEntity<CargoDto> cadastraCargo(@RequestBody CargoDto cargoDto) {
		ResponseEntity<CargoDto> response = ResponseEntity.ok().build();
		service.cadastraCargo(cargoDto);
		return response;
	}
	
	@DeleteMapping("/{nome}")
	public ResponseEntity<CargoDto> removeCargo(CargoDto cargoDto) {
		ResponseEntity<CargoDto> response = ResponseEntity.ok().build();
		service.removeCargo(cargoDto);
		return response;
	}
	
	@PutMapping("/{nome}")
	public ResponseEntity<CargoDto> atualizaCargo(@PathVariable String nome, @RequestBody CargoDto cargoDto){
		ResponseEntity<CargoDto> response = ResponseEntity.internalServerError().build();
		response = ResponseEntity.ok(service.atualizaCargo(nome, cargoDto));
		return response;
	}
}

