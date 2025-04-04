package com.miniware.blog.api.chat.service;

import com.miniware.blog.api.chat.dto.request.ChatMessageDto;

public interface ChatService {

    Long createRoom(String name);
    void sendMessage(ChatMessageDto message);

}
