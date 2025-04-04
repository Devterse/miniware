package com.miniware.blog.api.chat.redis;

import com.miniware.blog.api.chat.dto.request.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisPublisher {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ChannelTopic topic;

    public void publish(ChatMessageDto message) {
        //메세지를 Redis Pub/Sub 채널에 발행
        //발행된 메세지는 모든 서버에 있는 Redis 구독자(RedisSubscriber)에게 전송됨
        redisTemplate.convertAndSend(topic.getTopic(), message);     // Redis 채널에 메시지 발행
    }

}
