package br.gov.sgtservice.repository.search;

import br.gov.sgtservice.domain.elasticsearch.AnexoDocument;

public interface AnexoSearchRepository extends BasicElasticRepository<AnexoDocument, Integer> {
    @Override
    default Class<AnexoDocument> getEntity() {
        return AnexoDocument.class;
    }
}
