package com.pavliuchenko.messageprovider.controller;

import com.pavliuchenko.messageprovider.model.web.SignIn;
import com.pavliuchenko.messageprovider.model.web.SignUp;
import com.pavliuchenko.messageprovider.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/sign-in", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public Mono<Void> signIn(SignIn model) {
        log.info("User {} was successfully logged in!", model.getUsername());
        return Mono.empty();
    }

    @PostMapping("/sign-up")
    public Mono<Void> signUp(@RequestBody SignUp model) {
        return userService.create(model.getUsername(), model.getPassword(), model.getFullName());
    }

}
