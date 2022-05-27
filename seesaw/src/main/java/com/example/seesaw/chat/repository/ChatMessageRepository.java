package com.example.seesaw.chat.repository;

import com.example.seesaw.chat.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage,Long> {

    List<ChatMessage> findTOP20ByChatRoom_AreaOrderByCreatedAtDesc(String area);

}
