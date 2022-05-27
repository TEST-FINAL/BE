package com.example.seesaw.chat.dto;

import com.example.seesaw.chat.model.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDto {

    private String status;          // JOIN, TALK 타입
    private String senderName;      // 보내는사람
    private String message;         // 메세지 내용
    private LocalDateTime createdAt;
    // check하기.
    private String roomId;

    public ChatMessageDto(ChatMessage chatMessage){
        this.senderName = chatMessage.getSenderName();
        this.message = chatMessage.getMessage();
        this.createdAt = chatMessage.getCreatedAt();
    }

}
