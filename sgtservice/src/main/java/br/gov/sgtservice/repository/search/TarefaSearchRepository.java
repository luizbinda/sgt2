package br.gov.sgtservice.repository.search;


import br.gov.sgtservice.domain.elasticsearch.TarefaDocument;

public interface TarefaSearchRepository extends BasicElasticRepository<TarefaDocument, Integer> {

    @Override
    default Class<TarefaDocument> getEntity() {
        return TarefaDocument.class;
    }
}
