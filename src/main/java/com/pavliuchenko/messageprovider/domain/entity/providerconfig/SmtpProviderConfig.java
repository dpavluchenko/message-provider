package com.pavliuchenko.messageprovider.domain.entity.providerconfig;

import lombok.*;
import org.springframework.data.relational.core.mapping.Column;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SmtpProviderConfig extends MailProviderConfig {
    private String host;
    private int port;
    private boolean starttls;
    @Column("sender_address")
    private String senderAddress;
    private String username;
    private String password;
}
