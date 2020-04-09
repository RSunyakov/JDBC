package ru.kpfu.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.models.Marker;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MarkerDto {
    private Long id;
    private String color;
    private Integer condition;
    private AuditoriumDto auditorium;

    public static MarkerDto from (Marker marker) {
        return MarkerDto.builder()
                .id(marker.getId())
                .color(marker.getColor())
                .condition(marker.getCondition())
                .auditorium(AuditoriumDto.from(marker.getAuditorium()))
                .build();
    }

    public static List<MarkerDto> from(List<Marker> markers) {
        return markers.stream()
                .map(MarkerDto::from)
                .collect(Collectors.toList());
    }
}
