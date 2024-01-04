package sideproject.puddy.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class DogTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    @ManyToOne
    @JoinColumn(name = "dog_id")
    private Dog dog;
    private LocalDate createdAt;

    public DogTag(String content, Dog dog) {
        this.content = content;
        this.dog = dog;
        this.createdAt = LocalDate.now();
    }
}
