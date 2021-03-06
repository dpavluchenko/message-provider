package com.pavliuchenko.messageprovider.domain.entity;

import lombok.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("message")
public class Message extends BaseEntity{
    @Column("content")
    private String content;
}
