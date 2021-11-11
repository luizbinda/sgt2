package br.gov.sgtservice.service;



import br.gov.sgtservice.domain.Responsavel;
import br.gov.sgtservice.repository.ResponsavelRepository;
import br.gov.sgtservice.repository.search.ResponsavelSearchRepository;
import br.gov.sgtservice.service.dto.ResponsavelDTO;
import br.gov.sgtservice.service.event.ResponsavelEvent;
import br.gov.sgtservice.service.exception.RegraNegocioException;
import br.gov.sgtservice.service.filter.ResponsavelFilter;
import br.gov.sgtservice.service.mapper.ResponsavelListMapper;
import br.gov.sgtservice.service.mapper.ResponsavelMapper;
import br.gov.sgtservice.service.utils.ConstantsUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ResponsavelService {

  private final ResponsavelRepository responsavelRepository;
  private final ResponsavelSearchRepository responsavelSearchRepository;
  private final ResponsavelMapper responsavelMapper;
  private final ResponsavelListMapper responsavelListMapper;
  private final ApplicationEventPublisher applicationEventPublisher;


  public Page<ResponsavelDTO> index(ResponsavelFilter filter, Pageable pageable) {
    return responsavelSearchRepository.search(filter.getFilter(), pageable).map(responsavelListMapper::toDTO);
  }

  public ResponsavelDTO show(Integer id) {
    Responsavel responsavel = responsavelRepository.findById(id).orElseThrow(
            () -> new RegraNegocioException(ConstantsUtils.RESPONSAVEL_NOT_FOUND)
    );
    return responsavelMapper.toDTO(responsavel);
  }

  public ResponsavelDTO save(ResponsavelDTO reponsavelDTO) {
    Responsavel responsavel = responsavelMapper.toEntity(reponsavelDTO);
    responsavelRepository.save(responsavel);
    applicationEventPublisher.publishEvent(new ResponsavelEvent(responsavel.getId()));
    return responsavelMapper.toDTO(responsavel);
  }

  public void delete(Integer id) {
    Responsavel responsavel = responsavelRepository.findById(id).orElseThrow(
            () -> new RegraNegocioException(ConstantsUtils.RESPONSAVEL_NOT_FOUND)
    );
    responsavelRepository.delete(responsavel);
  }

}
