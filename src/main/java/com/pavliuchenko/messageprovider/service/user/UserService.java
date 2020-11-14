package com.pavliuchenko.messageprovider.service.user;

import reactor.core.publisher.Mono;

public interface UserService {
    Mono<Void> create(String username, String password, String fullName);
}
