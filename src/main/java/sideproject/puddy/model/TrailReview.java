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
public class TrailReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer star;
    @Column(length = 1000)
    private String content;
    @ManyToOne
    @JoinColumn(name = "reviewer_id")
    private Person reviewer;
    @ManyToOne
    @JoinColumn(name = "trail_id")
    private Trail trail;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    public TrailReview(Integer star, String content, Person reviewer, Trail trail) {
        this.star = star;
        this.content = content;
        this.reviewer = reviewer;
        this.trail = trail;
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }
    public TrailReview updateTrailReview(Integer star, String content){
        this.star = star;
        this.content = content;
        this.updatedAt = LocalDate.now();
        return this;
    }
}
