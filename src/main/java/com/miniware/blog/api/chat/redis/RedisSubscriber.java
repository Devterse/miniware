package com.miniware.blog.api.chat.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miniware.blog.api.chat.dto.request.ChatMessageDto;
import com.miniware.blog.api.chat.entity.ChatMessage;
import com.miniware.blog.api.chat.entity.ChatRoom;
import com.miniware.blog.api.chat.repository.ChatRoomRepository;
import com.miniware.blog.api.chat.service.ChatSaveService;
import com.miniware.blog.api.user.entity.User;
import com.miniware.blog.api.user.exception.UserException;
import com.miniware.blog.api.user.repository.UserRepository;
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
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final ChatSaveService chatSaveService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onMessage(Message message, byte[] pattern) {

        try {
            ChatMessageDto dto = objectMapper.readValue(message.getBody(), ChatMessageDto.class);
            messagingTemplate.convertAndSend("/topic/chatroom/" + dto.getChatRoomId(), dto);

            ChatRoom chatRoom = chatRoomRepository.findById(dto.getChatRoomId()).orElseThrow();
            User user = userRepository.findById(dto.getUserId()).orElseThrow(UserException::notFound);

            ChatMessage chatMessage = ChatMessage.builder()
                    .chatRoom(chatRoom)
                    .sender(user)
                    .content(dto.getContent())
                    .build();

            chatSaveService.save(chatMessage);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
