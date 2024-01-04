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
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private Integer star;
    @ManyToOne
    @JoinColumn(name = "reviewer_id")
    private Person reviewer;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Person owner;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    public Review(String content, Integer star, Person reviewer, Person owner) {
        this.content = content;
        this.star = star;
        this.reviewer = reviewer;
        this.owner = owner;
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }
    public Review updateReview(String content, Integer star){
        this.content = content;
        this.star = star;
        this.updatedAt = LocalDate.now();
        return this;
    }
}
