package sideproject.puddy.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 1000)
    private String content;
    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Person sender;
    private LocalDateTime createdAt;

    public ChatMessage(Chat chat, Person sender, String content) {
        this.content = content;
        this.chat = chat;
        this.sender = sender;
        this.createdAt = LocalDateTime.now();
    }
}
