package br.gov.sgtservice.repository.search;


import br.gov.sgtservice.domain.elasticsearch.ResponsavelDocument;

public interface ResponsavelSearchRepository extends BasicElasticRepository<ResponsavelDocument, Integer> {
    @Override
    default Class<ResponsavelDocument> getEntity() {
        return ResponsavelDocument.class;
    }

}
