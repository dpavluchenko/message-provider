package com.pavliuchenko.messageprovider.repository;

import com.pavliuchenko.messageprovider.domain.entity.Message;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface MessageRepository extends ReactiveCrudRepository<Message, Long> {

}
