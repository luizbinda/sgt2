package br.gov.sgtservice.service.mapper;


import br.gov.sgtservice.domain.Tarefa;
import br.gov.sgtservice.service.dto.TarefaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TarefaMapper extends EntityMapper<Tarefa, TarefaDTO> {

    @Override
    @Mapping(source = "responsavelId", target = "responsavel.id")
    Tarefa toEntity(TarefaDTO tarefaDTO);
}
