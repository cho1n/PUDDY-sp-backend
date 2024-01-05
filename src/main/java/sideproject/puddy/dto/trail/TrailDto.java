package sideproject.puddy.dto.trail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class TrailDto {
    private Long id;
    private String name;
    private Double startLat;
    private Double startLong;
    private Double endLat;
    private Double endLong;
}
