package com.example.seesaw.model;

import com.example.seesaw.dto.ChatMessageDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class ChatMessage extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column
    private String senderName;

    @Column
    private String message;

    @ManyToOne
    @JoinColumn(name = "CHATROOM_ID")
    private ChatRoom chatRoom;

    public ChatMessage(ChatMessageDto chatMessageDto, ChatRoom chatRoom) {
        this.userId = chatMessageDto.getUserId();
        this.message = chatMessageDto.getMessage();
        this.senderName = chatMessageDto.getSenderName();
        this.chatRoom = chatRoom;
    }
}
