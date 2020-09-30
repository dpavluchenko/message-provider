package com.pavliuchenko.messageprovider.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("message")
public class Message {
    @Id
    @Column("id")
    private Long id;
    @Column("content")
    private String content;
}
