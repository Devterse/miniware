package com.miniware.blog.api.chat.dto.request;

import lombok.*;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDto {

    private Long chatRoomId;
    private  Long userId;
    private  String sender;
    private  String content;

}
