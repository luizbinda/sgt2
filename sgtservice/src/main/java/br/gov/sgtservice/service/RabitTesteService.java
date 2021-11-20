package br.gov.sgtservice.service;

import br.gov.sgtservice.service.rabbit.ConsumerTarget;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
@EnableBinding(ConsumerTarget.class)
public class RabitTesteService {

    @StreamListener(target = ConsumerTarget.BINDING_TESTE)
    private void recalculate(@Payload String number) {
        log.info("Event update parameter received {}", number);
    }

}
