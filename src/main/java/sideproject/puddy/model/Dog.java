package sideproject.puddy.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Dog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String gender;
    private Date birth;
    private String type;
    private Long registerNum;
    private boolean neuter;
    private boolean main;
    private String image;
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;
    @OneToMany(mappedBy = "dog", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DogTag> dogTags = new ArrayList<>();
    private LocalDate createdAt;
    private LocalDate updatedAt;

    public Dog(String name, String gender, Date birth, String type, Long registerNum, boolean neuter, boolean main, String image, Person person) {
        this.name = name;
        this.gender = gender;
        this.birth = birth;
        this.type = type;
        this.registerNum = registerNum;
        this.neuter = neuter;
        this.main = main;
        this.image = image;
        this.person = person;
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }
    public Dog updateDog(String image, String type, String gender, boolean neuter){
        this.image = image;
        this.type = type;
        this.gender = gender;
        this.neuter = neuter;
        this.updatedAt = LocalDate.now();
        return this;
    }
}
