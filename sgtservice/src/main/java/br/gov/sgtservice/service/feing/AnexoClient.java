package br.gov.sgtservice.service.feing;

import br.gov.sgtservice.service.dto.AnexoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "anexo", url = "${feign.client.anexo.url}")
public interface AnexoClient {

  @GetMapping("api/anexo/{hash}")
  AnexoDTO getAnexo(@PathVariable String hash);

  @PostMapping("api/anexo")
  void uploadAnexo(@RequestBody AnexoDTO anexoDTO);

  @DeleteMapping("api/anexo/{hash}")
  void deleteAnexo(@PathVariable String hash);


}
