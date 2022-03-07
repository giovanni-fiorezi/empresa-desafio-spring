package br.com.empresa.projeto.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import br.com.empresa.projeto.repository.CargoRepository;
import br.com.empresa.projeto.repository.FuncionarioRepository;

public class AbstractControllerIT implements BaseControllerIT{
	
	@Autowired
	protected TestRestTemplate restTemplate;
	
	@Autowired
	protected FuncionarioRepository funcionarioRepository;
	
	@Autowired
	protected CargoRepository cargoRepository;
	
}
