package com.pavliuchenko.messageprovider.service.message.impl;

import com.pavliuchenko.messageprovider.domain.MessageStatus;
import com.pavliuchenko.messageprovider.domain.entity.providerconfig.SmtpProviderConfig;
import com.pavliuchenko.messageprovider.model.message.MessageEvent;
import com.pavliuchenko.messageprovider.model.provider.request.EmailMessageSendData;
import com.pavliuchenko.messageprovider.service.message.MessageSender;
import com.pavliuchenko.messageprovider.service.provider.Provider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MessageSenderImpl implements MessageSender {

    private final Provider<EmailMessageSendData, SmtpProviderConfig> provider;

    @Override
    public Mono<Boolean> sendMessage(MessageEvent messageEvent) {
        var providerConfig = new SmtpProviderConfig("smtp.gmail.com",
                587,
                true,
                "noreply@server.com",
                "pavliuchenko.smtp.testing@gmail.com",
                "smtp12345");
        var messageSendData = new EmailMessageSendData(messageEvent.getMessageId(),
                messageEvent.getContent(),
                messageEvent.getRecipients(),
                "test");
        return provider
                .send(messageSendData, providerConfig)
                .map(messageSendResult -> messageSendResult.getMessageStatus().equals(MessageStatus.SUCCESS));
    }
}
