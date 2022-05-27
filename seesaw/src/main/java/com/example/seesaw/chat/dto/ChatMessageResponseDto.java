package com.example.seesaw.chat.dto;

import com.example.seesaw.chat.model.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
//부모클래스
public class ChatMessageResponseDto {

    private String senderName;
    private String message;
    private LocalDateTime createdAt;


    public ChatMessageResponseDto (ChatMessage chatMessage) {
        this.senderName = chatMessage.getSenderName();
        this.message = chatMessage.getMessage();
        this.createdAt = chatMessage.getCreatedAt();
    }
}