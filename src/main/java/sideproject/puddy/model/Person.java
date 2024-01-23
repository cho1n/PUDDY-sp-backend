package sideproject.puddy.model;

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
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String password;
    private boolean gender;
    private String mainAddress;
    private String subAddress;
    private LocalDate birth;
    private Double latitude;
    private Double longitude;
    private LocalDate createdAt;
    private LocalDate updatedAt;
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
    @OneToMany(mappedBy = "firstPerson", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Chat> chats = new ArrayList<>();
    @OneToMany(mappedBy = "secondPerson", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Chat> chated = new ArrayList<>();
    public Person(String login, String password, String mainAddress, String subAddress, LocalDate birth, boolean gender, Double latitude, Double longitude) {
        this.login = login;
        this.password = password;
        this.gender = gender;
        this.mainAddress = mainAddress;
        this.subAddress = subAddress;
        this.birth = birth;
        this.latitude = latitude;
        this.longitude = longitude;
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }
    public void updatePerson(
            String password,
            String mainAddress,
            String subAddress,
            LocalDate birth,
            boolean gender,
            Double latitude,
            Double longitude
    ) {
        this.password = password;
        this.gender = gender;
        this.mainAddress = mainAddress;
        this.subAddress = subAddress;
        this.birth = birth;
        this.latitude = latitude;
        this.longitude = longitude;
        this.updatedAt = LocalDate.now();
    }
}
