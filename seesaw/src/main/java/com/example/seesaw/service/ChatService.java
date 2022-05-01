package com.example.seesaw.service;

import com.example.seesaw.domain.ChatMessage;
import com.example.seesaw.domain.ChatRoom;
import com.example.seesaw.dto.ChatMessageDto;
import com.example.seesaw.dto.ChatMessageResponseDto;
import com.example.seesaw.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;

    // 메인페이지 채널 채팅 내역 조회
    public List<ChatMessageResponseDto> getMainMessage() {

        List<ChatMessage> chatMessageList = chatMessageRepository.findTOP20ByChatRoom_AreaOrderByCreatedAtDesc("main");

        List<ChatMessageResponseDto> chatMessageResponseDtoList = new ArrayList<>();
        for(ChatMessage chatMessage : chatMessageList) {
            ChatMessageResponseDto chatMessageResponseDto = new ChatMessageResponseDto(chatMessage);

            chatMessageResponseDtoList.add(chatMessageResponseDto);
        }
//        Collections.reverse(chatMessageResponseDtoList);
        return chatMessageResponseDtoList;
    }
    // 채팅 메세지 셋업 메소두
    // token 은 나중에 적어주자.
    public void chatSettingMethod(ChatMessageDto chatMessageDto, ChatRoom chatRoom) {
//        chatMessageDto.setCreatedAt(LocalDateTime.now());
        Long uid = 0L;
        String username = "";

        /* 토큰 정보 추출 */
//        if (!(String.valueOf(token).equals("Authorization")||String.valueOf(token).equals("null"))) {
//            log.info("token : {}",String.valueOf(token));
//            String tokenInfo = token.substring(7);
//            username = jwtDecoder.decodeUsername(tokenInfo);
////            uid = userRepository.findByUsername(username).get().getUid();
//        }

        if(chatMessageDto.getStatus().equals("JOIN")) {
            if(username!=""&&username!="null"){
                log.info("JOIN일때 {}",chatMessageDto.getSenderName());
                chatMessageDto.setMessage(chatMessageDto.getSenderName()+"님이 입장하셨습니다");
//                log.info("=== 연결 : {}",chatMessageDto.getPid());
            }

        } else if (chatMessageDto.getStatus().equals("OUT")) {
            if(username!=""&&username!="null"){
                log.info("OUT일때 {}",chatMessageDto.getSenderName());
                chatMessageDto.setMessage( chatMessageDto.getSenderName()+"님이 퇴장하셨습니다");
//                log.info("=== 연결끊김 : {}",chatMessageDto.getPid());
            }

        } else {
//            uid = chatMessageDto.getUid();
//            String career = userRepository.findByUid(uid).get().getCareer();
//            chatMessageDto.setCareer(career);
//
//            log.info("비속어 필터링 전 채팅 : {}",chatMessageDto.getMessage());
//            // 비속어 필터링 메소드
//            chatFilter(chatMessageDto);
//            log.info("비속어 필터링 후 채팅 : {}",chatMessageDto.getMessage());

            //채팅 메시지 저장
//            ChatMessage chatMessage = new ChatMessage(uid, chatMessageDto, chatRoom);
            ChatMessage chatMessage = new ChatMessage(chatMessageDto, chatRoom);
            chatMessageRepository.save(chatMessage);

        }

    }
}
