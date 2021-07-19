package com.pavliuchenko.messageprovider.service.provider;

import com.pavliuchenko.messageprovider.domain.entity.providerconfig.ProviderConfig;
import com.pavliuchenko.messageprovider.model.provider.request.MessageSendData;
import com.pavliuchenko.messageprovider.model.provider.response.MessageSendResult;
import reactor.core.publisher.Mono;

public interface Provider<T extends MessageSendData, E extends ProviderConfig> {
    Mono<MessageSendResult> send(T messageSendData, E providerConfig);
}
