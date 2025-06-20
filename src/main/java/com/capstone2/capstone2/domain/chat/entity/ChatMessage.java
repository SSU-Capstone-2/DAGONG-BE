package com.capstone2.capstone2.domain.chat.entity;

import com.capstone2.capstone2.domain.member.entity.Member;
import com.capstone2.capstone2.domain.model.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ChatMessage extends BaseEntity {

    public enum MessageType {
        TALK, SYSTEM, NOTICE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MessageType messageType;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", nullable = false)
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Member sender;

    public static ChatMessage talk(Member sender, ChatRoom chatRoom, String content) {
        return ChatMessage.builder()
                .chatRoom(chatRoom)
                .sender(sender)
                .messageType(MessageType.TALK)
                .content(content)
                .build();
    }

    public static ChatMessage system(ChatRoom chatRoom, String content) {
        return ChatMessage.builder()
                .chatRoom(chatRoom)
                .messageType(MessageType.SYSTEM)
                .content(content)
                .build();
    }

    public static ChatMessage notice(ChatRoom chatRoom, Member sender, String content) {
        return ChatMessage.builder()
                .chatRoom(chatRoom)
                .sender(sender)
                .messageType(MessageType.NOTICE)
                .content(content)
                .build();
    }

}