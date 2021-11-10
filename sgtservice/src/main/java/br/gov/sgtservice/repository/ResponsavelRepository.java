package br.gov.sgtservice.repository;


import br.gov.sgtservice.domain.Responsavel;
import br.gov.sgtservice.domain.elasticsearch.ResponsavelDocument;
import br.gov.sgtservice.repository.search.Reindexer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponsavelRepository extends JpaRepository<Responsavel, Integer>, Reindexer {

  @Query("select new br.gov.sgtservice.domain.elasticsearch.ResponsavelDocument(" +
          "r.id," +
          "r.dataNascimento," +
          "r.nome," +
          "r.email," +
          "r.status" +
          ") from Responsavel r" +
          " where r.id = :id"
  )
  ResponsavelDocument getById(@Param("id") Integer id);

  @Override
  @Query("select new br.gov.sgtservice.domain.elasticsearch.ResponsavelDocument(" +
          "r.id," +
          "r.dataNascimento," +
          "r.nome," +
          "r.email," +
          "r.status" +
          ") from Responsavel r ORDER BY r.id"
  )
  Page<ResponsavelDocument> reindexPage(Pageable pageable);

  @Override
  default String getEntity() {
    return "responsavel";
  }

}
