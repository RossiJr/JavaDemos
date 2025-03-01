package org.rossijr.kafkachatservice.service;

import org.rossijr.kafkachatservice.dto.ChatMessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;


/**
 * The purpose of this class is to consume messages from the Kafka, acting like a consumer.
 *
 * Also, forwards the consumed message to the WebSockets, allowing to have a "live" behavior in the chat.
 */
@Service
public class KafkaConsumerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

    // Used to send messages to the WebSockets clients
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public KafkaConsumerService(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    /**
     * Sends the message to WebSockets clients "/api/v1/socket/topic/messages".
     *
     * Also, uses the "chat-group" so multiple consumers can share messages.
     * @param messageDTO The message to be sent.
     */
    @KafkaListener(topics = "chat-message", groupId = "chat-group")
    public void consume(ChatMessageDTO messageDTO) {
        logger.info("Received message: {}", messageDTO);
        simpMessagingTemplate.convertAndSend("/api/v1/socket/topic/messages", messageDTO);
    }
}
