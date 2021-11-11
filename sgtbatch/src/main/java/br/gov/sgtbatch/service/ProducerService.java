package br.gov.sgtbatch.service;

import br.gov.sgtbatch.service.rabbit.ProducerSource;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@EnableBinding(ProducerSource.class)
@Service
@RequiredArgsConstructor
public class ProducerService {

    private final ProducerSource producerSource;

    public void message(Integer number) {
        Message<Integer> message = MessageBuilder.withPayload(number).build();
        producerSource.testeMessage().send(message);
    }

}
