package com.pavliuchenko.messageprovider.service.message.impl;

import com.pavliuchenko.messageprovider.domain.entity.Message;
import com.pavliuchenko.messageprovider.service.message.MessageSender;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MessageSenderImpl implements MessageSender {
    @Override
    public Mono<Void> sendMessage(Message message) {
        return Mono.empty();
    }
}
