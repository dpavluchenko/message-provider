package com.pavliuchenko.messageprovider.service.user;

import com.pavliuchenko.messageprovider.domain.entity.User;
import com.pavliuchenko.messageprovider.domain.security.Role;
import com.pavliuchenko.messageprovider.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<Void> create(String username, String password, String fullName) {
        var user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(Role.USER)
                .fullName(fullName)
                .build();
        return userRepository.save(user).then();
    }

}
