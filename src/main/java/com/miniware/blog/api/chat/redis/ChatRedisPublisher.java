package com.miniware.blog.api.chat.redis;

import com.miniware.blog.api.chat.constants.ChatChannelType;
import com.miniware.blog.api.chat.dto.response.ChatMessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatRedisPublisher {

    private final RedisTemplate<String, Object> redisTemplate;

    public void publish(ChatMessageResponse message) {
        String redisChannel = ChatChannelType.ROOM.getRedisChannel(message.getChatRoomId());
        redisTemplate.convertAndSend(redisChannel, message);     // Redis 채널에 메시지 발행
    }

}
