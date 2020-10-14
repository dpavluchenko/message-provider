package com.pavliuchenko.messageprovider.config.kafka;

import com.pavliuchenko.messageprovider.domain.entity.Message;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;

import java.util.Collections;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaConf {

    private final KafkaProperties kafkaProperties;

    @Value("${kafka.partitions:6}")
    private Integer partitionCount;

    @Bean
    public KafkaSender<Long, Message> producer(){
        var senderOptions = SenderOptions.<Long, Message>create(Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers(),
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class)
        )
                .maxInFlight(1024);
        return KafkaSender.create(senderOptions);
    }

    @Bean
    public KafkaReceiver<Long, Message> consumer() {
        var deserializer = new JsonDeserializer<>(Message.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);
        ReceiverOptions<Long, Message> receiverOptions = ReceiverOptions.<Long, Message>create(Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers(),
                ConsumerConfig.GROUP_ID_CONFIG, kafkaProperties.getConsumer().getGroupId(),
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class,
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaProperties.getConsumer().getAutoOffsetReset()
        ))
                .subscription(Collections.singletonList(kafkaProperties.getTemplate().getDefaultTopic()));

        return KafkaReceiver.create(receiverOptions.withValueDeserializer(deserializer));
    }

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name(kafkaProperties.getTemplate().getDefaultTopic())
                .partitions(partitionCount)
                .build();
    }
}
