package com.pavliuchenko.messageprovider.model.provider.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
public class EmailMessageSendData extends MessageSendData {
    private String subject;

    public EmailMessageSendData(String messageId, String content, List<String> recipients, String subject) {
        this.messageId = messageId;
        this.content = content;
        this.recipients = recipients;
        this.subject = subject;
    }
}
