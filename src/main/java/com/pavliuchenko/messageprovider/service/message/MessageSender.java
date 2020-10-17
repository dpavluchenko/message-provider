package com.pavliuchenko.messageprovider.service.message;

import com.pavliuchenko.messageprovider.domain.entity.Message;
import reactor.core.publisher.Mono;

public interface MessageSender {
    Mono<Void> sendMessage(Message message);
}
