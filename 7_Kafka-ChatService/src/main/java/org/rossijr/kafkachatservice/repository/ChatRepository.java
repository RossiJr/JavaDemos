package org.rossijr.kafkachatservice.repository;

import org.rossijr.kafkachatservice.model.ChatMessage;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

@Document(collection = "messages")
public interface ChatRepository extends MongoRepository<ChatMessage, String> {
    default List<ChatMessage> findAllByOrderByTimestampDescAsc() {
        return findAll(Sort.by(Sort.Order.desc("timestamp")));
    }
}
