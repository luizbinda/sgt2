package br.gov.sgtservice.service.mapper;

import br.gov.sgtservice.domain.elasticsearch.ResponsavelDocument;
import br.gov.sgtservice.service.dto.ResponsavelDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResponsavelListMapper extends EntityMapper<ResponsavelDocument, ResponsavelDTO> {
}
