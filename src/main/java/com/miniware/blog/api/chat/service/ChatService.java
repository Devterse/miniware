package com.miniware.blog.api.chat.service;

import com.miniware.blog.api.auth.model.CustomUserDetails;
import com.miniware.blog.api.chat.dto.request.ChatMessageRequest;

import java.security.Principal;

public interface ChatService {

    Long createRoom(String name);
    void sendMessage(ChatMessageRequest message, CustomUserDetails userDetails);

}
