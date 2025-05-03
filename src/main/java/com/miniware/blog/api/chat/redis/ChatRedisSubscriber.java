package com.miniware.blog.api.chat.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miniware.blog.api.chat.constants.ChatChannelType;
import com.miniware.blog.api.chat.dto.request.ChatMessageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatRedisSubscriber implements MessageListener {

    private final SimpMessagingTemplate messagingTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onMessage(Message message, byte[] pattern) {

        try {
            //채널
            String channel = new String(message.getChannel());
            //채널방 id 추출
            Long roomId = ChatChannelType.ROOM.extractRoomId(channel);
            //WebSocket 경로 생성
            ChatMessageRequest dto = objectMapper.readValue(message.getBody(), ChatMessageRequest.class);
            log.debug("dto : {}", dto.toString());
            log.debug("message : {}", message);
            String webSocketPath = ChatChannelType.ROOM.getWebSocketPath(roomId);

            messagingTemplate.convertAndSend(webSocketPath, dto);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
