package br.gov.sgtservice.service.elasticsearch;

import br.gov.sgtservice.domain.elasticsearch.AnexoDocument;
import br.gov.sgtservice.repository.AnexoRepository;
import br.gov.sgtservice.repository.search.AnexoSearchRepository;
import br.gov.sgtservice.service.event.AnexoEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AnexoElasticSearchService {
  private final AnexoRepository anexoRepository;
  private final AnexoSearchRepository anexoSearchRepository;

  @TransactionalEventListener(fallbackExecution = true)
  public void indexar(AnexoEvent event) {
    log.info("Indexando anexo: {}", event.getId());
    AnexoDocument document = anexoRepository.getById(event.getId());
    anexoSearchRepository.save(document);
  }



}
