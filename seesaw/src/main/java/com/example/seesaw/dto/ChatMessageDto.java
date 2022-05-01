package com.example.seesaw.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChatMessageDto {
    private String status; // JOIN, TALK 타입
    private String senderName; // 보내는사람
    private String message; // 메세지 내용
    private String area;
    private String age; // 나이
//    private Long pid;
//    private Long uid;

//    private LocalDateTime createdAt;
//    private String opposingUserName;

//    private long userCount;
    private String roomId;
}
