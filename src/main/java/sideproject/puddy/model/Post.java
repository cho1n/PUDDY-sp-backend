package sideproject.puddy.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;
    private String title;
    @Column(length = 1000)
    private String content;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PostLike> postLikes = new ArrayList<>();

    public Post(String title, String content, Person person) {
        this.title = title;
        this.content = content;
        this.person = person;
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }
    public Post updatePost(String title, String content){
        this.title = title;
        this.content = content;
        this.updatedAt = LocalDate.now();
        return this;
    }
}
