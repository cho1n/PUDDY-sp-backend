package sideproject.puddy.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Person sender;
    private LocalDate createdAt;

    public ChatMessage(Chat chat, Person sender, String content) {
        this.content = content;
        this.chat = chat;
        this.sender = sender;
        this.createdAt = LocalDate.now();
    }
}
