package com.pavliuchenko.messageprovider.service.message;

import com.pavliuchenko.messageprovider.model.message.MessageEvent;
import reactor.core.publisher.Mono;

public interface MessageSender {
    Mono<Boolean> sendMessage(MessageEvent messageEvent);
}
