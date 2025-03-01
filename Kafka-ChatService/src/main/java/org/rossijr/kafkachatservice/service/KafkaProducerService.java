package org.rossijr.kafkachatservice.service;

import org.rossijr.kafkachatservice.dto.ChatMessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * The purpose of this class is to send messages to the Kafka, acting like a producer.
 *
 * @see org.rossijr.kafkachatservice.config.KafkaConfig
 */
@Service
public class KafkaProducerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);
    private final KafkaTemplate<String, ChatMessageDTO> kafkaTemplate;

    @Autowired
    public KafkaProducerService(KafkaTemplate<String, ChatMessageDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Publishes the message to the Kafka topic "chat-message".
     * @param messageDTO The message to be sent.
     */
    public void sendMessage(ChatMessageDTO messageDTO) {
        logger.info("Sending message to Kafka {}", messageDTO);
        kafkaTemplate.send("chat-message", messageDTO);
    }
}
