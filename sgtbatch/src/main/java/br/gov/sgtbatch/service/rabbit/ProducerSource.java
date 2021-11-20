package br.gov.sgtbatch.service.rabbit;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface ProducerSource {

    String BINDING_TESTE = "producer-teste";
    String BINDING_TESTE2 = "producer-teste2";

    @Output(ProducerSource.BINDING_TESTE)
    MessageChannel testeMessage();

}
