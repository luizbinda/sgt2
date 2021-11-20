package br.gov.sgtservice.service.filter;

import br.gov.sgtservice.service.utils.ElasticsearchFilterUtils;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Getter
public class FilterBuilder  {

    private final BoolQueryBuilder queryBuilder;

    public static class Builder {

        private final BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        private String wrapStar(String value) {
            return "".concat(value.toLowerCase()).concat("");
        }

        private String getFilterString(String value) {
            return wrapStar(StringUtils.stripAccents(value.toLowerCase().trim()));
        }

        public Builder mustTermQuery(String field, String value) {
            new ElasticsearchFilterUtils<String>() {
                @Override
                public void filterCondition(BoolQueryBuilder queryBuilder, String field, String value) {
                    queryBuilder.must(QueryBuilders.termQuery(field, value));
                }
            }.doQuery(queryBuilder, field, value);
            return this;
        }

        public Builder mustNotTermQuery(String field, String value) {
            new ElasticsearchFilterUtils<String>() {
                @Override
                public void filterCondition(BoolQueryBuilder queryBuilder, String field, String value) {
                    queryBuilder.mustNot(QueryBuilders.termQuery(field, value));
                }
            }.doQuery(queryBuilder, field, value);
            return this;
        }

        public Builder wildcard(String field, String value) {
            new ElasticsearchFilterUtils<String>() {
                @Override
                public void filterCondition(BoolQueryBuilder queryBuilder, String field, String value) {
                    queryBuilder.must(QueryBuilders.wildcardQuery(field, "".concat(value.toLowerCase()).concat("")));
                }
            }.doQuery(queryBuilder, field, value);
            return this;
        }

        public Builder rangeQueryLocalDate(String field, LocalDate value1, LocalDate value2) {
            if (Objects.isNull(value1) || Objects.isNull(value2)) {
                return this;
            }

            queryBuilder.must(QueryBuilders.rangeQuery(field)
                .gte(value1.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .lte(value2.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
            );
            return this;
        }

        private void shouldWildCard(BoolQueryBuilder queryBuilder, List<String> fields, String query) {
            if (Objects.isNull(query) || CollectionUtils.isEmpty(fields)) {
                return;
            }
            fields.forEach(field -> queryBuilder.should(QueryBuilders.wildcardQuery(field, getFilterString(query))));
        }

        public Builder mustNotMatchQuery(String field, String value) {
            new ElasticsearchFilterUtils<String>() {
                @Override
                public void filterCondition(BoolQueryBuilder queryBuilder, String field, String value) {
                    queryBuilder.mustNot(QueryBuilders.matchQuery(field, value));
                }
            }.doQuery(queryBuilder, field, value);
            return this;
        }

        public Builder shouldTermQuery(String field, String value) {
            new ElasticsearchFilterUtils<String>() {
                @Override
                public void filterCondition(BoolQueryBuilder queryBuilder, String field, String value) {
                    queryBuilder.should(QueryBuilders.termQuery(field, value));
                }
            }.doQuery(queryBuilder, field, value);
            return this;
        }

        public Builder filterFields(List<String> fields, String query, BoolQueryBuilder queryBuilder, String ...args) {
            fields.addAll(Arrays.asList(args));
            shouldWildCard(queryBuilder, fields, query);
            return this;
        }

        public BoolQueryBuilder build() {
            return new FilterBuilder(this).getQueryBuilder();
        }

    }

    public FilterBuilder(Builder builder) {
        this.queryBuilder = builder.queryBuilder;
    }

}
