package com.miniware.blog.api.chat.controller;

import com.miniware.blog.api.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
@Controller
@RequiredArgsConstructor
//@RequestMapping("/api/v1/chat")
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/rooms")
    public ResponseEntity<Long> createRoom(@RequestBody String name) {
        Long roomId = chatService.createRoom(name);
        return ResponseEntity.ok(roomId);
    }


    /*@MessageMapping("/send")
    public void sendMessage(@Payload ChatMessageDto message, Principal principal) {
        System.out.println("🔥 메시지 도착: " + message);
        System.out.println("👤 사용자: " + principal.getName());

        //클라이언트가 WebSocket으로 /app/send에 메세지를 보내면 실행됨
        chatService.sendMessage(message);
    }*/

    /*@MessageMapping("/send")
    public void test(@Payload String raw) {
        System.out.println("✅ 진입은 했음: " + raw);
    }*/

    /*@MessageMapping("/send")
    public void test(@Payload String raw, Principal principal) {
        System.out.println("🔥 진입 성공: " + raw);
        System.out.println("👤 사용자: " + (principal != null ? principal.getName() : "NULL"));
    }*/

    @MessageMapping("/send")
    public void test() {
        System.out.println("✅ 진입 성공!");
    }
}
