package com.pavliuchenko.messageprovider.model.provider.request;

import lombok.Data;

import java.util.List;

@Data
public abstract class MessageSendData {

   protected String messageId;

   protected String content;

   protected List<String> recipients;

}
