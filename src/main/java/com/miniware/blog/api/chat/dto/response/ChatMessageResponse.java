package com.miniware.blog.api.chat.dto.response;

import com.miniware.blog.api.chat.constants.MessageStatus;
import com.miniware.blog.api.chat.constants.MessageType;
import com.miniware.blog.api.chat.entity.ChatMessage;
import lombok.*;

import java.time.LocalDateTime;

import static com.miniware.blog.api.chat.constants.MessageStatus.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ChatMessageResponse {

    private final String messageUuid;    // 메시지 ID(NanoID)
    private final Long messageId;        // DB ID
    private final Long chatRoomId;       // 채팅방 ID
    private final String content;        // 메시지 내용
    private final Long senderId;         // 보낸 사람 ID
    private final String senderName;     // 보낸 사람 이름
    private final MessageType type;      // 메시지 타입
    private final MessageStatus status;  // 메시지 상태
    private final LocalDateTime createdAt;  //메세지 생성 시간

    public static ChatMessageResponse from(ChatMessage message) {
        return ChatMessageResponse.builder()
                .messageUuid(message.getMessageUuid())
                .messageId(message.getId())
                .chatRoomId(message.getChatRoom().getId())
                .content(message.getContent())
                .senderId(message.getSender().getId())
                .type(message.getType())
                .status(message.getId()!= null ? DELIVERED : SENDING)
                .createdAt(message.getCreateAt())
                .build();
    }

}
