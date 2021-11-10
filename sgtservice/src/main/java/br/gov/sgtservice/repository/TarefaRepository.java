package br.gov.sgtservice.repository;


import br.gov.sgtservice.domain.Tarefa;
import br.gov.sgtservice.domain.elasticsearch.TarefaDocument;
import br.gov.sgtservice.repository.search.Reindexer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TarefaRepository extends JpaRepository<Tarefa, Integer>, Reindexer {

    @Query("select new br.gov.sgtservice.domain.elasticsearch.TarefaDocument(" +
            "t.id," +
            "t.dataInicio," +
            "t.dataInicioPrevista," +
            "t.dataTerminoPrevista," +
            "t.status," +
            "t.comentarios," +
            "t.tipo," +
            "t.titulo," +
            "t.descricao," +
            "t.responsavel.nome" +
            ") from Tarefa t" +
            " where t.id = :id"
    )
    TarefaDocument getById(@Param("id") Integer id);

    @Override
    @Query("select new br.gov.sgtservice.domain.elasticsearch.TarefaDocument(" +
            "t.id," +
            "t.dataInicio," +
            "t.dataInicioPrevista," +
            "t.dataTerminoPrevista," +
            "t.status," +
            "t.comentarios," +
            "t.tipo," +
            "t.titulo," +
            "t.descricao," +
            "t.responsavel.nome" +
            ") from Tarefa t ORDER BY t.id"
    )
    Page<TarefaDocument> reindexPage(Pageable pageable);

    @Override
    default String getEntity() {
        return "tarefa";
    }

}
