package br.gov.sgtservice.service;

import br.gov.sgtservice.domain.Tarefa;
import br.gov.sgtservice.repository.TarefaRepository;
import br.gov.sgtservice.service.dto.TarefaDTO;
import br.gov.sgtservice.service.event.TarefaEvent;
import br.gov.sgtservice.service.exception.RegraNegocioException;
import br.gov.sgtservice.service.mapper.TarefaMapper;
import br.gov.sgtservice.service.utils.ConstantsUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TarefaService {

  private final TarefaRepository tarefaRepository;
  private final TarefaMapper tarefaMapper;
  private final ApplicationEventPublisher applicationEventPublisher;

  public List<TarefaDTO> index() {
    return tarefaMapper.toDTO(tarefaRepository.findAll());
  }

  public TarefaDTO show(Integer id) {
    Tarefa tarefa = tarefaRepository.findById(id).orElseThrow(
            () -> new RegraNegocioException(ConstantsUtils.TAREFA_NOT_FOUND)
    );
    return tarefaMapper.toDTO(tarefa);
  }

  public TarefaDTO save(TarefaDTO reponsavelDTO) {
    Tarefa tarefa = tarefaMapper.toEntity(reponsavelDTO);
    tarefaRepository.save(tarefa);
    applicationEventPublisher.publishEvent(new TarefaEvent(tarefa.getId()));
    return tarefaMapper.toDTO(tarefa);
  }

  public void delete(Integer id) {
    Tarefa tarefa = tarefaRepository.findById(id).orElseThrow(
            () -> new RegraNegocioException(ConstantsUtils.TAREFA_NOT_FOUND)
    );
    tarefaRepository.delete(tarefa);
  }

}
