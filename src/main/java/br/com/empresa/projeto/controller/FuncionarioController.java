package br.com.empresa.projeto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.empresa.projeto.enums.HttpEnum;
import br.com.empresa.projeto.dto.FuncionarioDto;
import br.com.empresa.projeto.exception.MatriculaNaoEncontradaException;
import br.com.empresa.projeto.exception.ParametroInvalidoException;
import br.com.empresa.projeto.service.FuncionarioService;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioController {

	@Autowired
	private FuncionarioService service;

	@GetMapping
	public Page<FuncionarioDto> buscaTodosOsFuncionarios(@PageableDefault(size = 10) Pageable pageable) {
		Page<FuncionarioDto> funcionarios = Page.empty();

		try {
			return service.buscaTodosOsFuncionarios(pageable);
		} catch (Exception e) {
			funcionarios = Page.empty();
		}
		return funcionarios;
	}

	@GetMapping("/{matricula}")
	public ResponseEntity<FuncionarioDto> buscaFuncionarioPorMatricula(@PathVariable String matricula) {
		ResponseEntity<FuncionarioDto> response = ResponseEntity.ok().build();
		try {
			return ResponseEntity.ok(service.buscaFuncionarioPorMatricula(matricula));
		} catch (ParametroInvalidoException pie) {
			response = ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.header(HttpEnum.HEADER_MESSAGE.toString(), pie.getMessage()).build();
		} catch (Exception ex) {
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.header(HttpEnum.HEADER_MESSAGE.toString(), ex.getMessage()).build();
		}
		return response;
	}

	@PostMapping
	public ResponseEntity<FuncionarioDto> cadastraFuncionario(@RequestBody FuncionarioDto funcionario) {
		ResponseEntity<FuncionarioDto> response = ResponseEntity.internalServerError().build();

		try {
			response = ResponseEntity.ok(service.cadastraFuncionario(funcionario));
		} catch (MatriculaNaoEncontradaException cne) {
			response = ResponseEntity.status(HttpStatus.NOT_FOUND)
					.header(HttpEnum.HEADER_MESSAGE.toString(), cne.getMessage()).build();
		} catch (Exception e) {
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.header(HttpEnum.HEADER_MESSAGE.toString(), e.getMessage()).build();
		}
		return response;
	}

	@DeleteMapping("/{matricula}")
	public ResponseEntity<FuncionarioDto> removeFuncionario(FuncionarioDto funcionarioDto) {
		ResponseEntity<FuncionarioDto> response = ResponseEntity.ok().build();

		try {
			service.removeFuncionario(funcionarioDto);
		} catch (ParametroInvalidoException pie) {
			response = ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.header(HttpEnum.HEADER_MESSAGE.toString(), pie.getMessage()).build();
		} catch (Exception e) {
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.header(HttpEnum.HEADER_MESSAGE.toString(), e.getMessage()).build();
		}
		return response;
	}

	@PutMapping("/{matricula}")
	public ResponseEntity<FuncionarioDto> atualizaFuncionario(@PathVariable String matricula, @RequestBody FuncionarioDto funcionarioDto){
		ResponseEntity<FuncionarioDto> response = ResponseEntity.internalServerError().build();
		
		try {
			response = ResponseEntity.ok(service.atualizaFuncionario(matricula, funcionarioDto));
		} catch (MatriculaNaoEncontradaException mne) {
			response = ResponseEntity.status(HttpStatus.NOT_FOUND)
					.header(HttpEnum.HEADER_MESSAGE.toString(), mne.getMessage()).build();
		} catch (Exception e) {
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.header(HttpEnum.HEADER_MESSAGE.toString(), e.getMessage()).build();
		}
		return response;
	}
}
