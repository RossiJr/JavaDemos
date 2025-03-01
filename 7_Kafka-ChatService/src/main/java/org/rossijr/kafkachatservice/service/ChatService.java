package org.rossijr.kafkachatservice.service;

import org.rossijr.kafkachatservice.dto.ChatMessageDTO;
import org.rossijr.kafkachatservice.model.ChatMessage;
import org.rossijr.kafkachatservice.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {
    private final KafkaProducerService kafkaProducerService;
    private final ChatRepository chatRepository;

    @Autowired
    public ChatService(KafkaProducerService kafkaProducerService, ChatRepository chatRepository) {
        this.kafkaProducerService = kafkaProducerService;
        this.chatRepository = chatRepository;
    }

    /**
     * Processes and sends message through Kafka, and saves it to MongoDB.
     * @param sender
     * @param message
     */
    public void processAndSendMessage(String sender, String message) {
        ChatMessageDTO messageDTO = new ChatMessageDTO(sender, message);
        ChatMessage chatMessage = new ChatMessage(sender, message, System.currentTimeMillis());
        chatRepository.save(chatMessage);
        kafkaProducerService.sendMessage(messageDTO);
    }

    /**
     * Returns chat history from MongoDB.
     * @return List of ChatMessage objects.
     */
    public List<ChatMessage> getChatHistory(){
        return chatRepository.findAllByOrderByTimestampDescAsc();
    }
}
