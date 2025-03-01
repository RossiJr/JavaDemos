package org.rossijr.kafkachatservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic chatTopic() {
        // Sets up a topic named "chat-message" with 1 partition and 1 replica (if it doesn't exist, creates it)
        return new NewTopic("chat-message", 1, (short) 1);
    }
}
