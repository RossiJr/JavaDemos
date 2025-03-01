package org.rossijr.kafkachatservice.dto;

/**
 * Data Transfer Object used for sending chat messages.
 */
public class ChatMessageDTO {
    private String sender;
    private String message;

    public ChatMessageDTO() {
    }

    public ChatMessageDTO(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ChatMessageDTO{" +
                "sender='" + sender + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
