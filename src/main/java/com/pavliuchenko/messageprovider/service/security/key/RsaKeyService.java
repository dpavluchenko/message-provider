package com.pavliuchenko.messageprovider.service.security.key;


import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Service
public class RsaKeyService implements KeyService {

    private KeyPair keyPair;

    @PostConstruct
    @SneakyThrows
    private void init() {
        keyPair = create(readKeyFile("rsa-keys/public_key.der"), readKeyFile("rsa-keys/private_key.der"));
    }

    @Override
    public PublicKey getPublicKey() {
        return keyPair.getPublic();
    }

    @Override
    public Mono<byte[]> getEncodedPublicKey() {
        return Mono.just(keyPair.getPublic().getEncoded());
    }

    @Override
    public PrivateKey getPrivateKey() {
        return keyPair.getPrivate();
    }

    private KeyPair create(byte[] publicKey, byte[] privateKey) throws GeneralSecurityException {
        X509EncodedKeySpec publicSpec =
                new X509EncodedKeySpec(publicKey);
        PKCS8EncodedKeySpec privateSpec =
                new PKCS8EncodedKeySpec(privateKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return new KeyPair(keyFactory.generatePublic(publicSpec), keyFactory.generatePrivate(privateSpec));
    }

    private byte[] readKeyFile(String key) throws IOException {
        return IOUtils.toByteArray(new ClassPathResource(key).getInputStream());
    }

}
