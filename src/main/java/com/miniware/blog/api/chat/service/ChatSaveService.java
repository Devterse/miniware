package com.miniware.blog.api.chat.service;

import com.miniware.blog.api.chat.dto.request.ChatMessageDto;
import com.miniware.blog.api.chat.entity.ChatMessage;
import com.miniware.blog.api.chat.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatSaveService {

    private final ChatMessageRepository chatMessageRepository;

    @Async
    public void save(ChatMessage message) {
        chatMessageRepository.save(message);
    }

}
