package com.example.seesaw.domain;

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

    //    @Column(nullable = false)
//    private Long uid;
    @Column
    private String senderName;

    @Column
    private String message;

    @Column
    private String age; // 나이

//    @Column
//    private String opposingUserName;

    @ManyToOne
    @JoinColumn(name = "CHATROOM_ID")
    private ChatRoom chatRoom;

    //    public ChatMessage (ChatMessageDto chatMessageDto, ChatRoom chatRoom){
//        this.message = chatMessageDto.getMessage();
//        this.senderName=chatMessageDto.getSenderName();
////        this.opposingUserName=chatMessageDto.getOpposingUserName();
//        this.chatRoom=chatRoom;
//        this.age = chatMessageDto.getAge();
//    }
    public ChatMessage(ChatMessageDto chatMessageDto, ChatRoom chatRoom) {
        this.message = chatMessageDto.getMessage();
        this.senderName = chatMessageDto.getSenderName();
//        this.opposingUserName=chatMessageDto.getOpposingUserName();
        this.age = chatMessageDto.getAge();
        this.chatRoom = chatRoom;
    }
}
