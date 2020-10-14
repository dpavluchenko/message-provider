package com.pavliuchenko.messageprovider.service.message;

import com.pavliuchenko.messageprovider.domain.entity.Message;
import com.pavliuchenko.messageprovider.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class KafkaMessageProducerService implements MessageProducerService {

    private final KafkaSender<Long, Message> kafkaSender;
    private final NewTopic currentTopic;
    private final MessageRepository messageRepository;

    @Override
    public Flux<Boolean> sendMessage(String messageContent) {
        return kafkaSender.send(messageRepository.save(Message.builder()
                .content(messageContent).build())
                .map(message -> SenderRecord.create(new ProducerRecord<>(currentTopic.name(),
                                message.getId(),
                                message),
                        null)))
                .map(result -> nonNull(result.recordMetadata()));
    }
}
