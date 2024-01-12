package sideproject.puddy.dto.type;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TypeListDto {
    private List<TypeDto> dogTypes;
}
