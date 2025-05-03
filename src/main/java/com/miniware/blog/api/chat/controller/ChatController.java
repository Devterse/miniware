package com.miniware.blog.api.chat.controller;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.miniware.blog.api.auth.model.CustomUserDetails;
import com.miniware.blog.api.chat.dto.request.ChatMessageRequest;
import com.miniware.blog.api.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    //개톡방 생성
    //단톡방 생성
    //채팅방 목록조회
    //채팅방 상세 조회
    //채팅방 메세지 목록조회
    //채팅방 나가기


    @PostMapping("/rooms")
    public ResponseEntity<Long> createRoom(@RequestBody String name) {
        Long roomId = chatService.createRoom(name);
        return ResponseEntity.ok(roomId);
    }

    /**
     * WebSocket을 통해 메시지를 전송합니다.
     * 클라이언트가 WebSocket으로 /app/send에 메세지를 보내면 실행됨
     *
     * @param message 전송할 메시지
     */
    @MessageMapping("/chat/message")
    public void sendMessage(@Payload ChatMessageRequest message, Principal principal) {
        chatService.sendMessage(message, principal);
    }
}
