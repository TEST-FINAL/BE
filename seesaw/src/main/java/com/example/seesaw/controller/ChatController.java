package com.example.seesaw.controller;

import com.example.seesaw.domain.ChatRoom;
import com.example.seesaw.dto.ChatMessageDto;
import com.example.seesaw.repository.ChatRoomRepository;
import com.example.seesaw.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatService chatService;

    @MessageMapping("/mainchat") // ../mainchat 이라는 api 메세지를 보내면 mainMessage 메소드가 호출. 목적지 /pub 으로 했으니 stompClient.send("/pub/mainchat")
    @SendTo("/topic/mainchat") // topic/mainchat   api 를 구독하고 있는 클라이언트들에게 mainMessage 된다.  subscribe("/topic/mainchat")
    public ChatMessageDto mainMessage(ChatMessageDto chatMessageDto) throws Exception {
//        log.info("채팅테스트:{}",chatMessageDto.getMessage());

//        log.info("채팅 헤더 확인:{}",token);

        Thread.sleep(100); // simulated delay

        ChatRoom chatRoom = new ChatRoom();

        // 채팅방있는지 확인 후 없으면 생성, 있으면 채팅방 변수에 할당해놓음 -> 채팅 저장 시에 사용할 예정
        if(!(chatRoomRepository.findByArea("main").isPresent())) {
            chatRoom = new ChatRoom("main");
            chatRoomRepository.save(chatRoom);
        } else {
            chatRoom = chatRoomRepository.findByArea("main").orElseThrow(
                    ()-> new IllegalArgumentException("에러!!")
            );
        }
        //채팅 메시지 셋업 메소드  (token은 나중에)
        chatService.chatSettingMethod(chatMessageDto, chatRoom);

        String destination = "mainchat";
        log.info("=== channel : {}",destination);

//        chatMessageDto.setUserCount(redisChatRepository.getUserCount(destination));

        return chatMessageDto;
    }
}