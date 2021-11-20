package br.gov.sgtservice.domain.elasticsearch;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.InnerField;
import org.springframework.data.elasticsearch.annotations.MultiField;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Document(indexName = "gerenciador-de-tarefas-responsavel")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponsavelDocument extends BaseDocument {

  private static final String SORT = "sort";

  @MultiField(mainField = @Field(type = FieldType.Text, store = true, fielddata = true),
    otherFields = {@InnerField(suffix = SORT, type = FieldType.Date, store = true, format = DateFormat.custom, pattern = DATE_PATTERN)
  })
  private String dataNascimento;

  @MultiField(mainField = @Field(type = FieldType.Text, store = true, fielddata = true),
          otherFields = {@InnerField(suffix = SORT, type = FieldType.Text, store = true)}
  )
  private String nome;

  @MultiField(mainField = @Field(type = FieldType.Text, store = true, fielddata = true),
          otherFields = {@InnerField(suffix = SORT, type = FieldType.Text, store = true)}
  )
  private String email;

  @MultiField(mainField = @Field(type = FieldType.Boolean, store = true),
          otherFields = {@InnerField(suffix = SORT, type = FieldType.Text, store = true)}
  )
  private Boolean status;

  public ResponsavelDocument(Integer id, LocalDate dataNascimento, String nome, String email, Boolean status) {
    super(id);
    this.dataNascimento = Objects.nonNull(dataNascimento) ? dataNascimento.format(DateTimeFormatter.ofPattern(DATE_PATTERN)) : null;
    this.nome = nome;
    this.email = email;
    this.status = status;
  }

}
