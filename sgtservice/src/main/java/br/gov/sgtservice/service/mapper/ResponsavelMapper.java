package br.gov.sgtservice.service.mapper;

import br.gov.sgtservice.domain.Responsavel;
import br.gov.sgtservice.service.dto.ResponsavelDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResponsavelMapper extends EntityMapper<Responsavel, ResponsavelDTO> {
}
