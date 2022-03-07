package br.com.empresa.projeto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.empresa.projeto.entity.CargoEntity;

@Repository
public interface CargoRepository extends JpaRepository<CargoEntity, Long> {

	public void deleteByNome(String nome);

	Optional<CargoEntity> findByNome(String nome);

}
