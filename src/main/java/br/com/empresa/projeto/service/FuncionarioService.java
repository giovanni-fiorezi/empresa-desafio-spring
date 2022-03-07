package br.com.empresa.projeto.service;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.empresa.projeto.dto.FuncionarioDto;
import br.com.empresa.projeto.entity.FuncionarioEntity;
import br.com.empresa.projeto.exception.MatriculaJaCadastradaException;
import br.com.empresa.projeto.exception.MatriculaNaoEncontradaException;
import br.com.empresa.projeto.exception.ParametroInvalidoException;
import br.com.empresa.projeto.exception.ServiceException;
import br.com.empresa.projeto.repository.FuncionarioRepository;

@Service
public class FuncionarioService {

	@Autowired
	FuncionarioRepository repository;

	@Autowired
	ModelMapper mapper;

	private Logger log = LoggerFactory.getLogger(FuncionarioService.class);

	public Page<FuncionarioDto> buscaTodosOsFuncionarios(Pageable pageable) throws ServiceException {
		log.info("Buscando todos os Funcionários de forma listada");

		try {
			Page<FuncionarioEntity> funcionariosEntity = repository.findAll(pageable);
			return funcionariosEntity.map(func -> mapper.map(func, FuncionarioDto.class));
		} catch (Exception ex) {
			log.error("Ocorreu um erro inesperado, não foi possível buscar todos os clientes");
			throw new ServiceException("Ocorreu um erro inesperado, não foi possível buscar todos os clientes", ex);
		}
	}

	public FuncionarioDto buscaFuncionarioPorMatricula(String matricula) throws ServiceException {
		log.info("Buscando funcionário por Matrícula");
		FuncionarioDto funcionarioDto;

		if (matricula.length() != 5) {
			log.error("Matrícula informada não é válida {}", matricula);
			throw new ParametroInvalidoException("Matrícula informada não é válida");
		}
		try {
			FuncionarioEntity funcionarioEntity = repository.findByMatricula(matricula)
					.orElseThrow(() -> new MatriculaNaoEncontradaException(
							"Não foi possível encontrar um funcionário com a matrícula informada"));
			funcionarioDto = mapper.map(funcionarioEntity, FuncionarioDto.class);
		} catch (MatriculaNaoEncontradaException mne) {
			log.error("Não foi possível encontrar um Funcionário com essa matrícula {}", mne);
			throw new MatriculaNaoEncontradaException(mne.getMessage(), mne);
		} catch (Exception ex) {
			log.error("Ocorreu um erro inesperado, não foi possível buscar funcionário por matrícula");
			throw new ServiceException("Ocorreu um erro inesperado, não foi possível buscar funcionário por matrícula",
					ex);
		}
		return funcionarioDto;
	}

	@Transactional
	public FuncionarioDto cadastraFuncionario(FuncionarioDto funcionarioDto) throws Exception {
		log.info("Cadastra Funcionário");

		if (repository.findByMatricula(funcionarioDto.getMatricula()).isPresent()) {
			log.error("Já existe um funcionário cadastrado com essa matrícula {}", funcionarioDto.getMatricula());
			throw new MatriculaJaCadastradaException("Funcionário informado já encontra-se cadastrado");
		}
		try {
			FuncionarioEntity entity = mapper.map(funcionarioDto, FuncionarioEntity.class);
			FuncionarioEntity entitySalva = repository.save(entity);
			funcionarioDto = mapper.map(entitySalva, FuncionarioDto.class);
		} catch (Exception ex) {
			log.error("Ocorreu um erro, não foi possível cadastrar esse Funcionário");
			throw new ServiceException("Ocorreu um erro inesperado, não foi possível cadastrar esse Funcionário", ex);
		}
		return funcionarioDto;
	}

	@Transactional
	public void removeFuncionario(FuncionarioDto funcionario) throws ServiceException{
		log.info("Removendo um funcionário");
		
		if (funcionario.getMatricula().length() != 7) {
			log.error("Matrícula informada não é valida {}", funcionario.getMatricula());
			throw new ParametroInvalidoException("Matrícula informada não é válida");
		}
		
		try {
			repository.findByMatricula(funcionario.getMatricula()).orElseThrow( 
					() -> new MatriculaNaoEncontradaException("Não foi possível encontrar um funcionário com a matrícula informada"));
			repository.deleteByMatricula(funcionario.getMatricula());
		} catch (MatriculaNaoEncontradaException mne) {
			log.error("Não foi possível encontrar um Funcionário com essa matrícula {}", mne);
			throw new MatriculaNaoEncontradaException(mne.getMessage(), mne);
		} catch (Exception ex) {
			log.error("Ocorreu um erro inesperado, não foi possível buscar funcionário por matrícula");
			throw new ServiceException("Ocorreu um erro inesperado, não foi possível buscar funcionário por matrícula", ex);
		}
	}

	@Transactional
	public FuncionarioDto atualizaFuncionario(String matricula, FuncionarioDto funcionarioDto) throws ServiceException {
		log.info("Atualizando funcionário");

		try {
			FuncionarioEntity funcionario = repository.findByMatricula(funcionarioDto.getMatricula())
					.orElseThrow(() -> new MatriculaNaoEncontradaException(
							"Não foi possível encontrar um funcionário com a matrícula informada"));
			BeanUtils.copyProperties(funcionarioDto, funcionario);
			FuncionarioEntity save = repository.save(funcionario);
			funcionarioDto = mapper.map(save, FuncionarioDto.class);
		} catch (MatriculaNaoEncontradaException mne) {
			log.error("Não foi possível encontrar um Funcionário com essa matrícula {}", mne);
			throw new MatriculaNaoEncontradaException(mne.getMessage(), mne);
		} catch (Exception ex) {
			log.error("Ocorreu um erro inesperado, não foi possível atualizar esse funcionário");
			throw new ServiceException("Ocorreu um erro inesperado, não foi possível atualizar esse funcionário", ex);
		}
		return funcionarioDto;
	}

}
