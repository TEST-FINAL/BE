package com.example.seesaw.dto;

import com.example.seesaw.domain.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ChatMessageResponseDto {
    private String senderName;
    private String message;
//    private String opposingUserName;
    private LocalDateTime createdAt;
    private String age;

    public ChatMessageResponseDto (ChatMessage chatMessage) {
        this.senderName = chatMessage.getSenderName();
        this.message = chatMessage.getMessage();
//        this.opposingUserName = chatMessage.getOpposingUserName();
        this.createdAt = chatMessage.getCreatedAt();
        this.age=chatMessage.getAge();
    }
}
