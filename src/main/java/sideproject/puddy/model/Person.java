package sideproject.puddy.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String login;
    String password;
    String gender;
    String mainAddress;
    String subAddress;
    Date birth;
    Date createdAt;
    Date updatedAt;
    String refreshToken;
    public Person(String login, String password, String gender, String mainAddress, String subAddress, Date birth) {
        this.login = login;
        this.password = password;
        this.gender = gender;
        this.mainAddress = mainAddress;
        this.subAddress = subAddress;
        this.birth = birth;
    }
    public Person updatePerson(String login, String password, String gender, String mainAddress, String subAddress, Date birth) {
        this.login = login;
        this.password = password;
        this.gender = gender;
        this.mainAddress = mainAddress;
        this.subAddress = subAddress;
        this.birth = birth;
        this.updatedAt = new Date();
        return this;
    }
    public Person updateToken(String refreshToken){
        this.refreshToken = refreshToken;
        return this;
    }
}
