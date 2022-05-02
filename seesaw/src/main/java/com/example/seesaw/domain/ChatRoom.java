package com.example.seesaw.domain;

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
    private String area; // 채팅창? 이거 잘 모르겠네

    @Column
    private Long pid;

//    @Column
//    private Long uid;

    public ChatRoom(String area) {
        this.area = area;
    }
}
