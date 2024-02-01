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
    private Long chatId;
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Person sender;
    private LocalDate createdAt;

    public ChatMessage(Long chatId, Person sender, String content) {
        this.content = content;
        this.chatId = chatId;
        this.sender = sender;
        this.createdAt = LocalDate.now();
    }
}
