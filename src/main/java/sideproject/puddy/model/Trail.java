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
public class Trail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double startLat;
    private Double endLat;
    private Double startLong;
    private Double endLong;
    private String name;
    private LocalDate createdAt;
    @OneToMany(mappedBy = "trail", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TrailReview> trailReviews = new ArrayList<>();

    public Trail(Double startLat, Double endLat, Double startLong, Double endLong, String name) {
        this.startLat = startLat;
        this.endLat = endLat;
        this.startLong = startLong;
        this.endLong = endLong;
        this.name = name;
        this.createdAt = LocalDate.now();
    }
}
