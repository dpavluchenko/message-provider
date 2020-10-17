package com.pavliuchenko.messageprovider.service.message.impl;

import com.pavliuchenko.messageprovider.domain.entity.Message;
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

    private final KafkaReceiver<Long, Message> kafkaReceiver;
    private final MessageSender messageSender;

    @PostConstruct
    private void init() {
        kafkaReceiver.receive()
                .flatMap(record -> messageSender.sendMessage(record.value()))
                .subscribe();
    }
}
