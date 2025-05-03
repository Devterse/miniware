package com.miniware.blog.api.chat.entity;

import com.miniware.blog.api.chat.constants.MessageType;
import com.miniware.blog.api.common.entity.BaseEntity;
import com.miniware.blog.api.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Table(name = "tb_chat_message")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String messageUuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", nullable = false)
    private ChatRoom chatRoom;  //메시지가 속한 채팅방

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;    //메시지 발송자

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; //메시지 내용

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MessageType type;

    @Builder
    public ChatMessage(String messageUuid, ChatRoom chatRoom, User sender, String content, MessageType type) {
        this.messageUuid = messageUuid;
        this.chatRoom = chatRoom;
        this.sender = sender;
        this.content = content;
        this.type = type;
    }
}
