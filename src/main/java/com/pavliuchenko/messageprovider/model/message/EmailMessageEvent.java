package com.pavliuchenko.messageprovider.model.message;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class EmailMessageEvent extends MessageEvent {
    private String subject;
}
