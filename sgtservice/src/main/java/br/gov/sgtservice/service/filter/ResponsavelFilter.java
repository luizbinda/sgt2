package br.gov.sgtservice.service.filter;

import lombok.Getter;
import lombok.Setter;
import org.elasticsearch.index.query.BoolQueryBuilder;

import java.io.Serializable;

@Getter
@Setter
public class ResponsavelFilter implements Serializable {

    protected String query;

    public BoolQueryBuilder getFilter() {
        return new FilterBuilder.Builder()
            .mustTermQuery("nome", query)
            .build();
    }

}
