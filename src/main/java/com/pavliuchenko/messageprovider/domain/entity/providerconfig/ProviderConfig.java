package com.pavliuchenko.messageprovider.domain.entity.providerconfig;

import com.pavliuchenko.messageprovider.domain.entity.BaseEntity;
import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Table("provider_config")
public abstract class ProviderConfig extends BaseEntity {
    private String name;
    private String userId;
}
