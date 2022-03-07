package br.com.empresa.projeto.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.empresa.projeto.dto.FuncionarioDto;
import br.com.empresa.projeto.entity.FuncionarioEntity;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
class CargoRepositoryTest  extends AbstractControllerIT{
	
	private static final String FUNCIONARIO = "/funcionario/";
	private static final String MATRICULA_VALIDA = "12345";
	private static final String MATRICULA_APAGADA = "6789033";
	
	private static StringBuilder path = new StringBuilder(URL);
	
	@Test
	void esperaEncontrarUmFuncionarioComAMatriculaInformada() {
		path.append(MATRICULA_VALIDA);
		ParameterizedTypeReference<FuncionarioDto> responseType = new ParameterizedTypeReference<>() {
		};
		
		ResponseEntity<FuncionarioDto> response = this.restTemplate.exchange(path.toString(), HttpMethod.GET, 
				null, responseType);
		
		Optional<FuncionarioEntity> funcionario = funcionarioRepository.findByMatricula(MATRICULA_VALIDA);
		assertEquals(MATRICULA_VALIDA, funcionario.get().getMatricula());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	void esperaCadastrarUmFuncionarioComAMatriculaInformada() {
		
	}
	
	@Test
	void esperaDeletarUmFuncionarioComAmatriculaInformada() {
		path.append(MATRICULA_APAGADA);
		ParameterizedTypeReference<FuncionarioDto> responseType = new ParameterizedTypeReference<>(){
		};
		
		ResponseEntity<FuncionarioDto> response = this.restTemplate.exchange(path.toString(), HttpMethod.DELETE, 
				null, responseType);
		funcionarioRepository.deleteByMatricula(MATRICULA_APAGADA);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@BeforeEach
	void inicializar() {
		path.append(FUNCIONARIO);
	}

	@AfterEach
	void finalizar() {
		path = new StringBuilder(URL);
	}
}
