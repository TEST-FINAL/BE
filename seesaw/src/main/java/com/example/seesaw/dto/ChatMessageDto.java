package com.example.seesaw.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class ChatMessageDto {

    private String status;          // JOIN, TALK 타입
    private String senderName;      // 보내는사람
    private String message;         // 메세지 내용
    private LocalDateTime createdAt;



    // check하기.
    private String roomId;

}
