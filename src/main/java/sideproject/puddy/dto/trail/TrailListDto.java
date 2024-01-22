package sideproject.puddy.dto.trail;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TrailListDto {
    private List<TrailDto> trails;
    private double myLat;
    private double myLong;
    private String myMainAddress;
}
