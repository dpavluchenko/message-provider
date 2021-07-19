package com.pavliuchenko.messageprovider.service.message.impl;

import com.pavliuchenko.messageprovider.domain.entity.Message;
import com.pavliuchenko.messageprovider.model.message.EmailMessageEvent;
import com.pavliuchenko.messageprovider.model.message.MessageEvent;
import com.pavliuchenko.messageprovider.repository.MessageRepository;
import com.pavliuchenko.messageprovider.service.message.MessageProducerService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

import java.util.List;
import java.util.Map;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class KafkaMessageProducerService implements MessageProducerService {

    private final KafkaSender<Long, MessageEvent> kafkaSender;
    private final NewTopic currentTopic;
    private final MessageRepository messageRepository;

    @Override
    public Flux<Boolean> produceMessage(Map<String, String> messageInfo) {
        return kafkaSender.send(
                messageRepository.save(new Message(messageInfo.get("content")))
                        .map(message -> SenderRecord.create(new ProducerRecord<>(currentTopic.name(),
                                        message.getId(),
                                        EmailMessageEvent.builder()
                                                .recipients(List.of(messageInfo.get("recipient")))
                                                .content(message.getContent())
                                                .build()),
                                null)))
                .map(result -> nonNull(result.recordMetadata()));
    }
}
