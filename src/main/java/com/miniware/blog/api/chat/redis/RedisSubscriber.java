package com.miniware.blog.api.chat.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miniware.blog.api.chat.dto.request.ChatMessageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class RedisSubscriber implements MessageListener {

    private final SimpMessagingTemplate messagingTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onMessage(Message message, byte[] pattern) {

        try {
            ChatMessageRequest dto = objectMapper.readValue(message.getBody(), ChatMessageRequest.class);
            messagingTemplate.convertAndSend("/topic/chatroom/" + dto.getChatRoomId(), dto);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
