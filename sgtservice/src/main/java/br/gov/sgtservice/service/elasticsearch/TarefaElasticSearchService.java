package br.gov.sgtservice.service.elasticsearch;

import br.gov.sgtservice.domain.elasticsearch.TarefaDocument;
import br.gov.sgtservice.repository.TarefaRepository;
import br.gov.sgtservice.repository.search.TarefaSearchRepository;
import br.gov.sgtservice.service.event.TarefaEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TarefaElasticSearchService {
  private final TarefaRepository tarefaRepository;
  private final TarefaSearchRepository tarefaSearchRepository;

  @TransactionalEventListener(fallbackExecution = true)
  public void indexar(TarefaEvent event) {
    log.info("Indexando tarefa: {}", event.getId());
    TarefaDocument document = tarefaRepository.getById(event.getId());
    tarefaSearchRepository.save(document);
  }



}
