package br.gov.sgtservice.service.mapper;


import br.gov.sgtservice.domain.Anexo;
import br.gov.sgtservice.service.dto.AnexoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {TarefaMapper.class})
public interface AnexoMapper extends EntityMapper<Anexo, AnexoDTO> {

    @Override
    @Mapping(target = "tarefa.id", source = "tarefaId")
    Anexo toEntity(AnexoDTO anexoDTO);
}
