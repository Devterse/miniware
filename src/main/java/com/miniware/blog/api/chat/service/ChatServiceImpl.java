package com.miniware.blog.api.chat.service;

import com.miniware.blog.api.auth.model.CustomUserDetails;
import com.miniware.blog.api.auth.security.SecurityUtil;
import com.miniware.blog.api.chat.dto.request.ChatMessageRequest;
import com.miniware.blog.api.chat.dto.response.ChatMessageResponse;
import com.miniware.blog.api.chat.entity.ChatMessage;
import com.miniware.blog.api.chat.entity.ChatRoom;
import com.miniware.blog.api.chat.redis.ChatRedisPublisher;
import com.miniware.blog.api.chat.repository.ChatMessageRepository;
import com.miniware.blog.api.chat.repository.ChatRoomRepository;
import com.miniware.blog.api.common.generator.IdGenerator;
import com.miniware.blog.api.user.entity.User;
import com.miniware.blog.api.user.exception.UserException;
import com.miniware.blog.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.security.Principal;

import static com.miniware.blog.api.chat.constants.MessageType.TALK;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final ChatRedisPublisher chatRedisPublisher;
    private final IdGenerator idGenerator;

    @Override
    public Long createRoom(String name) {
        ChatRoom chatRoom = ChatRoom.builder()
                .name(name)
                .build();
        return chatRoomRepository.save(chatRoom).getId();
    }

    @Override
    public void sendMessage(ChatMessageRequest request, CustomUserDetails userDetails) {

        //1. 사용자 정보 가져오기
        String username = userDetails.getUsername();
        User sender = userRepository.findByUsername(username).orElseThrow(UserException::notFound);

        //2. 채팅방 정보 가져오기
        ChatRoom chatRoom = chatRoomRepository.findById(request.getChatRoomId()).orElseThrow();

        //3. 메세지 id 생성
        String messageUuid = idGenerator.generateId();

        //4. 메시지 엔티티 생성
        ChatMessage chatMessage = ChatMessage.builder()
                .messageUuid(messageUuid)
                .chatRoom(chatRoom)
                .sender(sender)
                .content(request.getContent())
                .type(TALK)
                .build();

        //5. 비동기로 DB에 저장
        saveMessageAsync(chatMessage);

        //6. Redis Pub/Sub으로 즉시 메시지 전송
        ChatMessageResponse response = ChatMessageResponse.builder()
                .chatRoomId(chatRoom.getId())
                .messageUuid(chatMessage.getMessageUuid())
                .content(chatMessage.getContent())
                .senderId(sender.getId())
                .senderName(sender.getUsername())
                .type(TALK)
                .build();

        chatRedisPublisher.publish(response);

    }

    /**
     * 비동기로 메시지 저장
     */
    @Async
    protected void saveMessageAsync(ChatMessage message) {
        chatMessageRepository.save(message);
    }
}
