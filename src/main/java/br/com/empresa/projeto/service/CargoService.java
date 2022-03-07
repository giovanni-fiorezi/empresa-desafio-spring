package br.com.empresa.projeto.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.empresa.projeto.dto.CargoDto;
import br.com.empresa.projeto.entity.CargoEntity;
import br.com.empresa.projeto.repository.CargoRepository;

@Service
public class CargoService {
	
	@Autowired
	CargoRepository repository;
	
	@Autowired
	ModelMapper mapper;
	
	public List<CargoDto> buscaTodosOsCargos(){
		List<CargoDto> dto = repository.findAll().stream().map(func -> toDto(func)).collect(Collectors.toList());
		return dto;
	}
	
	public CargoDto buscaCargoPorNome (String nome) throws Exception{
		Optional<CargoEntity> optional = repository.findByNome(nome);
		CargoEntity entity = optional.orElseThrow(() -> new Exception("Teste"));
		return toDto(entity);
	}
	
	@Transactional
	public CargoDto cadastraCargo(CargoDto cargoDto) {
		repository.findByNome(cargoDto.getNome());
		CargoEntity entity = mapper.map(cargoDto, CargoEntity.class);
		CargoEntity entitySalva = repository.save(entity);
		cargoDto = mapper.map(entitySalva, CargoDto.class);
		return cargoDto;
	}
	
	@Transactional
	public void removeCargo(CargoDto cargoDto) {
		repository.deleteByNome(cargoDto.getNome());
	}
	
	@Transactional
	public CargoDto atualizaCargo(String nome, CargoDto cargoDto) {
		CargoEntity cargo = repository.findByNome(nome).get();
		CargoDto cargoDtoOld = toDto(cargo);
		BeanUtils.copyProperties(cargoDto, cargoDtoOld, "cargo");
		CargoEntity convertEntity = toEntity(cargoDto);
		convertEntity.setId(cargo.getId());
		CargoEntity cargoUpdate = repository.save(convertEntity);
		return toDto(cargoUpdate);
	}
	
	private CargoEntity toEntity(CargoDto dto) {
		return mapper.map(dto, CargoEntity.class);
	}
	
	private CargoDto toDto(CargoEntity entity) {
		return mapper.map(entity, CargoDto.class);
	}
}

