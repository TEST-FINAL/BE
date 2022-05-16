package com.example.seesaw.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class ChatRoom extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomId; // 방 아이디

    @Column(nullable = false)
    private String area;


    public ChatRoom(String area) {
        this.area = area;
    }
}
