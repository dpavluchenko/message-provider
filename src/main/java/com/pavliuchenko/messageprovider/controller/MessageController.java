package com.pavliuchenko.messageprovider.controller;

import com.pavliuchenko.messageprovider.service.message.MessageProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageProducerService messageProducerService;

    @PostMapping
    public Flux<Boolean> create(@RequestBody String message) {
        return messageProducerService.sendMessage(message);
    }

}
