package br.gov.sgtbatch.service;

import br.gov.sgtbatch.service.rabbit.ProducerSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@EnableBinding(ProducerSource.class)
@Service
@RequiredArgsConstructor
@Slf4j
public class ProducerService {
    private final ProducerSource producerSource;

    @Scheduled(cron = "*/02 * * * * *")
    public void message() {
        Message<LocalDateTime> message = MessageBuilder.withPayload(LocalDateTime.now()).build();
        log.info("teste.... {}", message.getPayload());
        producerSource.testeMessage().send(message);
    }

}
