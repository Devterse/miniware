package com.miniware.blog.api.chat.service;

import com.miniware.blog.api.chat.dto.request.ChatMessageRequest;

public interface ChatService {

    Long createRoom(String name);
    void sendMessage(ChatMessageRequest message);

}
