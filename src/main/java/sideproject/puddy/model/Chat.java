package sideproject.puddy.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "firstPerson_id")
    @Nullable
    private Person firstPerson;
    @ManyToOne
    @JoinColumn(name = "secondPerson_id")
    @Nullable
    private Person secondPerson;
    private LocalDate createdAt;
    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChatMessage> messages = new ArrayList<>();

    public Chat(Person firstPerson, Person secondPerson) {
        this.firstPerson = firstPerson;
        this.secondPerson = secondPerson;
        this.createdAt = LocalDate.now();
    }
    public void deletePerson(Person person){
        if (person == firstPerson){
            this.firstPerson = null;
        } else {
            this.secondPerson = null;
        }
    }
}
