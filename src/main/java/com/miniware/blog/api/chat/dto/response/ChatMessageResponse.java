package com.miniware.blog.api.chat.dto.response;

import com.miniware.blog.api.chat.constants.MessageStatus;
import com.miniware.blog.api.chat.constants.MessageType;
import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ChatMessageResponse {

    private final Long messageId;        // 메시지 ID
    private final Long chatRoomId;       // 채팅방 ID
    private final String content;        // 메시지 내용
    private final Long senderId;         // 보낸 사람 ID
    private final String senderName;     // 보낸 사람 이름
    private final MessageType type;      // 메시지 타입
    private final MessageStatus status;  // 메시지 상태



}
