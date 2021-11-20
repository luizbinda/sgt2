package br.gov.sgtservice.service.utils;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import java.util.Objects;

public abstract class ElasticsearchFilterUtils<T> {

    public void doQuery(BoolQueryBuilder queryBuilder, String field, T value) {
        if (Objects.isNull(value) || value instanceof String && StringUtils.isBlank((String) value)) {
            return;
        }
        filterCondition(queryBuilder, field, value);
    }

    public abstract void filterCondition(BoolQueryBuilder queryBuilder, String field, T value);

}
