package sideproject.puddy.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 1000)
    private String content;
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    public Comment(String content, Person person, Post post) {
        this.content = content;
        this.person = person;
        this.post = post;
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }
    public Comment updateComment(String content){
        this.content = content;
        this.updatedAt = LocalDate.now();
        return this;
    }
}
