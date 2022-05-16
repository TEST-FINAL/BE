package com.example.seesaw.service;

import com.example.seesaw.dto.ChatMessageDto;
import com.example.seesaw.dto.ChatMessageResponseDto;
import com.example.seesaw.model.ChatMessage;
import com.example.seesaw.model.ChatRoom;
import com.example.seesaw.repository.ChatMessageRepository;
import com.example.seesaw.security.jwt.JwtDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final JwtDecoder jwtDecoder;

    // 메인페이지 채팅 내역 조회
    public List<ChatMessageResponseDto> getMainChat() {

        List<ChatMessage> chatMessageList = chatMessageRepository.findTOP20ByChatRoom_AreaOrderByCreatedAtDesc("main");

        List<ChatMessageResponseDto> chatMessageResponseDtoList = new ArrayList<>();
        for(ChatMessage chatMessage : chatMessageList) {
            ChatMessageResponseDto chatMessageResponseDto = new ChatMessageResponseDto(chatMessage);

            chatMessageResponseDtoList.add(chatMessageResponseDto);
        }
        Collections.reverse(chatMessageResponseDtoList);
        return chatMessageResponseDtoList;
    }

    // 채팅 메세지
    public void chatSave(ChatMessageDto chatMessageDto, String token ,ChatRoom chatRoom) {
//        String username = "";

        // 토큰 추출
        if (!(String.valueOf(token).equals("Authorization"))) {
            log.info("token : {}",String.valueOf(token));
            String tokenInfo = token.substring(7); // Bearer빼고
//            username = jwtDecoder.decodeUsername(tokenInfo);
        }

        if(chatMessageDto.getStatus().equals("JOIN")) {
//            if(username!=""&&username!="null"){
                log.info("JOIN일때 {}",chatMessageDto.getSenderName());
                chatMessageDto.setMessage(chatMessageDto.getSenderName()+"님이 입장하셨습니다");
//            }

        }else if (chatMessageDto.getStatus().equals("OUT")) {
//            if(username!=""&&username!="null") {
                log.info("OUT일때 {}", chatMessageDto.getSenderName());
                chatMessageDto.setMessage(chatMessageDto.getSenderName() + "님이 퇴장하셨습니다");
//            }
        } else {
            //채팅 메시지 저장
            ChatMessage chatMessage = new ChatMessage(chatMessageDto, chatRoom);
            chatMessageRepository.save(chatMessage);

        }
    }
}
