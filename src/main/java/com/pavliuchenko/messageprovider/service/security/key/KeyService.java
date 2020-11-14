package com.pavliuchenko.messageprovider.service.security.key;

import reactor.core.publisher.Mono;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface KeyService {
    PublicKey getPublicKey();
    Mono<byte[]> getEncodedPublicKey();
    PrivateKey getPrivateKey();
}
