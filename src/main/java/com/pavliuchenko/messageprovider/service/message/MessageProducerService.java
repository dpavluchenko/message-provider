package com.pavliuchenko.messageprovider.service.message;

import reactor.core.publisher.Flux;

import java.util.Map;

public interface MessageProducerService {
    Flux<Boolean> produceMessage(Map<String, String> messageInfo);
}
