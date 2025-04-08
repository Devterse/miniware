package com.miniware.blog.api.chat.dto.request;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatMessageRequest {

    private Long chatRoomId;
    private  String content;

}
