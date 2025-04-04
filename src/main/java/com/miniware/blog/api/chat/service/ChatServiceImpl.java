package com.miniware.blog.api.chat.service;

import com.miniware.blog.api.chat.dto.request.ChatMessageDto;
import com.miniware.blog.api.chat.entity.ChatRoom;
import com.miniware.blog.api.chat.redis.RedisPublisher;
import com.miniware.blog.api.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final RedisPublisher redisPublisher;
    private final ChatRoomRepository chatRoomRepository;

    @Override
    public Long createRoom(String name) {
        ChatRoom chatRoom = ChatRoom.builder()
                .name(name)
                .build();
        return chatRoomRepository.save(chatRoom).getId();
    }

    @Override
    public void sendMessage(ChatMessageDto chatMessage) {
        //메시지를 Redis로 발행(Publish)
        //저장은 안 하고 실시간 전송 목적만 담당
        redisPublisher.publish(chatMessage);
    }
}
