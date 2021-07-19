package com.pavliuchenko.messageprovider.model.provider.response;

import com.pavliuchenko.messageprovider.domain.MessageStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageSendResult {
    private String messageId;
    private MessageStatus messageStatus;
}
