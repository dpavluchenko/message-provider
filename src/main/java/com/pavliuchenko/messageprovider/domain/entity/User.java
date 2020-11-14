package com.pavliuchenko.messageprovider.domain.entity;

import com.pavliuchenko.messageprovider.domain.security.Role;
import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("user")
public class User extends BaseEntity {
    private String username;
    private String password;
    private String fullName;
    private Role role;
}
