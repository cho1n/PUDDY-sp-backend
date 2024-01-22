package sideproject.puddy.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "firstPerson_id")
    private Person firstPerson;
    @ManyToOne
    @JoinColumn(name = "secondPerson_id")
    private Person secondPerson;
    private LocalDate createdAt;

    public Chat(Person firstPerson, Person secondPerson) {
        this.firstPerson = firstPerson;
        this.secondPerson = secondPerson;
        this.createdAt = LocalDate.now();
    }
}
