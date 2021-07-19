package com.pavliuchenko.messageprovider.service.provider;

import com.pavliuchenko.messageprovider.domain.MessageStatus;
import com.pavliuchenko.messageprovider.domain.entity.providerconfig.SmtpProviderConfig;
import com.pavliuchenko.messageprovider.model.provider.request.EmailMessageSendData;
import com.pavliuchenko.messageprovider.model.provider.response.MessageSendResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Properties;

@Service
public class SmtpMailProvider implements Provider<EmailMessageSendData, SmtpProviderConfig> {

    @Value("${mail-provider.javamail.smtp.auth:true}")
    private boolean smtpAuth;

    @Value("${mail-provider.javamail.transport.protocol:smtp}")
    private String transportProtocol;

    @Value("${mail-provider.javamail.debug:true}")
    private boolean mailDebug;

    @Value("${mail.smtp.connectiontimeout:60000}")
    private String smtpConnectionTimeout;


    @Override
    public Mono<MessageSendResult> send(EmailMessageSendData messageSendData, SmtpProviderConfig providerConfig) {
        var sender = new JavaMailSenderImpl();
        var props = new Properties();
        props.put("mail.smtp.auth", smtpAuth);
        props.put("mail.smtp.connectiontimeout", smtpConnectionTimeout);
        props.put("mail.transport.protocol", transportProtocol);
        props.put("mail.debug", mailDebug);
        props.put("mail.smtp.starttls.enable", true);

        sender.setJavaMailProperties(props);
        sender.setHost(providerConfig.getHost());
        sender.setPort(providerConfig.getPort());
        sender.setPassword(providerConfig.getPassword());
        sender.setUsername(providerConfig.getUsername());

        var mimeMessage = sender.createMimeMessage();
        return Mono.fromCallable(() -> {
            try {
                var mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                mimeMessageHelper.setSubject(messageSendData.getSubject());
                mimeMessageHelper.setTo(messageSendData.getRecipients().toArray(String[]::new));
                mimeMessageHelper.setText(messageSendData.getContent(), true);

                sender.send(mimeMessage);

                return MessageSendResult.builder()
                        .messageId(messageSendData.getMessageId())
                        .messageStatus(MessageStatus.SUCCESS)
                        .build();
            } catch (Exception e) {
                return MessageSendResult.builder()
                        .messageId(messageSendData.getMessageId())
                        .messageStatus(MessageStatus.REJECTED)
                        .build();
            }
        });
    }

}
