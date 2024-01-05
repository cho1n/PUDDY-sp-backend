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
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String password;
    private String gender;
    private String mainAddress;
    private String subAddress;
    private Date birth;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private String refreshToken;
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PostLike> postLikes = new ArrayList<>();
    @OneToMany(mappedBy = "reviewer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TrailReview> trailReviews = new ArrayList<>();
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Dog> dogs = new ArrayList<>();
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Match> matches = new ArrayList<>();
    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Match> matched = new ArrayList<>();
    public Person(String login, String password, String mainAddress, String subAddress, Date birth, String gender) {
        this.login = login;
        this.password = password;
        this.gender = gender;
        this.mainAddress = mainAddress;
        this.subAddress = subAddress;
        this.birth = birth;
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }
    public Person updatePerson(String login, String password, String mainAddress, String subAddress, Date birth, String gender) {
        this.login = login;
        this.password = password;
        this.gender = gender;
        this.mainAddress = mainAddress;
        this.subAddress = subAddress;
        this.birth = birth;
        this.updatedAt = LocalDate.now();
        return this;
    }
    public void updateToken(String refreshToken){
        this.refreshToken = refreshToken;
    }
}
