package com.miniware.blog.api.chat.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miniware.blog.api.chat.constants.ChatChannelType;
import com.miniware.blog.api.chat.dto.response.ChatMessageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

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
        String webSocketPath = ChatChannelType.ROOM.getWebSocketPath(roomId);

        String msgBody = new String(message.getBody());

        ChatMessageResponse dto = objectMapper.readValue(msgBody, ChatMessageResponse.class);

        messagingTemplate.convertAndSend(webSocketPath, dto);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
