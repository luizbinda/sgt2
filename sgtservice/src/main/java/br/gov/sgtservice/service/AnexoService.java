package br.gov.sgtservice.service;

import br.gov.sgtservice.domain.Anexo;
import br.gov.sgtservice.repository.AnexoRepository;
import br.gov.sgtservice.service.dto.AnexoDTO;
import br.gov.sgtservice.service.event.AnexoEvent;
import br.gov.sgtservice.service.exception.RegraNegocioException;
import br.gov.sgtservice.service.feing.AnexoClient;
import br.gov.sgtservice.service.mapper.AnexoMapper;
import br.gov.sgtservice.service.utils.ConstantsUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AnexoService {

  private final AnexoRepository anexoRepository;
  private final AnexoMapper anexoMapper;
  private final AnexoClient anexoClient;
  private final ApplicationEventPublisher applicationEventPublisher;

  public List<AnexoDTO> index() {
    return anexoMapper.toDTO(anexoRepository.findAll());
  }

  public AnexoDTO show(Integer id) {
    Anexo anexo = anexoRepository.findById(id).orElseThrow(
            () -> new RegraNegocioException(ConstantsUtils.ANEXO_NOT_FOUND)
    );
    return anexoClient.getAnexo(anexo.getHash());
  }

  public AnexoDTO save(AnexoDTO anexoDTO) {
    anexoDTO.setHash(UUID.randomUUID().toString());
    Anexo anexo = anexoMapper.toEntity(anexoDTO);
    anexoClient.uploadAnexo(anexoDTO);
    anexoRepository.save(anexo);
    applicationEventPublisher.publishEvent(new AnexoEvent(anexo.getId()));
    return anexoMapper.toDTO(anexo);
  }

  public void delete(Integer id) {
    Anexo anexo = anexoRepository.findById(id).orElseThrow(
            () -> new RegraNegocioException(ConstantsUtils.ANEXO_NOT_FOUND)
    );
    anexoClient.deleteAnexo(anexo.getHash());
    anexoRepository.delete(anexo);
  }

}
