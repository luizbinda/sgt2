package br.gov.sgtservice.service.elasticsearch;


import br.gov.sgtservice.repository.search.BasicElasticRepository;
import br.gov.sgtservice.repository.search.Reindexer;
import br.gov.sgtservice.service.exception.RegraNegocioException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

@Service("ElasticsearchReindexService1")
@RequiredArgsConstructor
@Slf4j
public class ElasticsearchReindexService {

  @Value("${nuvem.elasticsearch.reindex.pageSize:10000}")
  private Integer pageSize;

  private final List<Reindexer> reindexadores;
  private final List<BasicElasticRepository> repositories;
  private final ElasticsearchRestTemplate elasticsearchTemplate;
  private final ElasticsearchOperations elasticsearchOperations;

  @Transactional(readOnly = true)
  @Async
  public void reindex() {
    log.info("Starting reindex.");
    for (Reindexer reindexer : reindexadores) {
      reindex(reindexer);
    }
  }

  @Transactional(readOnly = true)
  @Async
  public void reindexEntity(String entity) {
    log.info("Starting reindex entity: {}", entity);
    for (Reindexer reindexer : reindexadores) {
      if(reindexer.getEntity().equals(entity)) {
        reindex(reindexer);
      }
    }
  }

  private void reindex(Reindexer bean) {
    Pageable pageable = PageRequest.of(0, pageSize);
    Page<?> page = bean.reindexPage(pageable);
    log.info("Objects found {}.", page.getTotalElements());
    if (!page.hasContent()) {
      return;
    }
    log.info("Total Pages {}.", page.getTotalPages());
    BasicElasticRepository searchRepository = getSearchRepository(page);
    recreateIndexDocument(searchRepository.getEntity());

    while (page.hasContent()) {
      log.info("Page Number {}.", page.getNumber());
      searchRepository.saveAll(page);
      page = bean.reindexPage(page.getPageable().next());
    }

    log.info("Finish reindex of {}.", bean.getEntity());
  }

  private BasicElasticRepository getSearchRepository(Page<?> page) {
    Class documentClass = page.getContent().get(0).getClass();
    Iterator<BasicElasticRepository> var3 = this.repositories.iterator();

    BasicElasticRepository searchRepository;
    do {
      if (!var3.hasNext()) {
        throw new RegraNegocioException("Falha de Reindexa????o...");
      }
      searchRepository = var3.next();
    } while(!searchRepository.getEntity().equals(documentClass));

    return searchRepository;
  }

  private <T> void recreateIndexDocument(Class<T> entityClass) {
    log.info("Recriate index class: {}", entityClass.getName());
    elasticsearchOperations.deleteIndex(entityClass);
    elasticsearchOperations.createIndex(entityClass);
    elasticsearchTemplate.putMapping(entityClass);
  }

}
