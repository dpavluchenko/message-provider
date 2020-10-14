package com.pavliuchenko.messageprovider.service.message;

import com.pavliuchenko.messageprovider.domain.entity.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.kafka.receiver.KafkaReceiver;

@Service
@Slf4j
public class MessageConsumerServiceImpl implements MessageConsumerService {

    private final KafkaReceiver<Long, Message> kafkaReceiver;

    public MessageConsumerServiceImpl(KafkaReceiver<Long, Message> kafkaReceiver) {
        this.kafkaReceiver = kafkaReceiver;

        this.kafkaReceiver.receive().doOnNext(message -> processMessage(message.value()))
                .subscribe();
    }

    private void processMessage(Message message) {
        log.info(message.toString());
    }
}
