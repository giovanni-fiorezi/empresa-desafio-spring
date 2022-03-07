package br.com.empresa.projeto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.empresa.projeto.entity.FuncionarioEntity;

@Repository
public interface FuncionarioRepository extends JpaRepository<FuncionarioEntity, Long>{
	
	public void deleteByMatricula(String matricula);
	
	Optional<FuncionarioEntity> findByMatricula(String matricula);

}