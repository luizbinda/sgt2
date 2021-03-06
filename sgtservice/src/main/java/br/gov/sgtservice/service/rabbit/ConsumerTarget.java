package br.gov.sgtservice.service.rabbit;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface ConsumerTarget {

    String BINDING_TESTE = "consumer-teste";

    @Input(ConsumerTarget.BINDING_TESTE)
    SubscribableChannel testeFila();

}
