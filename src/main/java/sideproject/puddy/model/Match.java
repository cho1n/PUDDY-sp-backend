package sideproject.puddy.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Person sender;
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private Person receiver;
    private LocalDate createdAt;

    public Match(Person sender, Person receiver) {
        this.sender = sender;
        this.receiver = receiver;
        this.createdAt = LocalDate.now();
    }


}
