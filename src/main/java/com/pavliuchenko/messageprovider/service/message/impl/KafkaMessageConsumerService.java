package com.pavliuchenko.messageprovider.service.message.impl;

import com.pavliuchenko.messageprovider.model.message.MessageEvent;
import com.pavliuchenko.messageprovider.service.message.MessageConsumerService;
import com.pavliuchenko.messageprovider.service.message.MessageSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.kafka.receiver.KafkaReceiver;

import javax.annotation.PostConstruct;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaMessageConsumerService implements MessageConsumerService {

    private final KafkaReceiver<Long, MessageEvent> kafkaReceiver;
    private final MessageSender messageSender;

    @PostConstruct
    private void init() {
        kafkaReceiver.receive()
                .flatMap(messageRecord -> messageSender.sendMessage(messageRecord.value()))
                .subscribe();
    }
}
