package com.example.seesaw.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class ChatMessageDto {
    private String status; // JOIN, TALK 타입
    private String senderName; // 보내는사람
    private String message; // 메세지 내용
    private Long userId;
    private LocalDateTime createdAt;
    private String roomId;

}
