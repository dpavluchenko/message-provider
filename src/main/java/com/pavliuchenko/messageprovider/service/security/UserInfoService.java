package com.pavliuchenko.messageprovider.service.security;

import com.pavliuchenko.messageprovider.domain.security.UserInfo;
import com.pavliuchenko.messageprovider.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Primary
@RequiredArgsConstructor
public class UserInfoService implements ReactiveUserDetailsService {

    private final UserRepository userRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new UsernameNotFoundException(String.format("User with name %s not found!", username)))))
                .map(user -> new UserInfo(user.getId(), user.getUsername(), user.getPassword(), user.getRole()));
    }
}
