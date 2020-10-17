package com.pavliuchenko.messageprovider.service.message;

import reactor.core.publisher.Flux;

public interface MessageProducerService {
    Flux<Boolean> produceMessage(String messageContent);
}
