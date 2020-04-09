package ru.kpfu.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.models.Auditorium;
import ru.kpfu.itis.models.Marker;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AuditoriumDto {
    private Long id;
    private String name;
    private List<Marker> markerList;
    private List<UserDto> users;

    public static AuditoriumDto from(Auditorium auditorium) {
        return AuditoriumDto.builder()
                .id(auditorium.getId())
                .name(auditorium.getName())
                .markerList(auditorium.getMarkerList())
                .users(UserDto.from(auditorium.getUsers()))
                .build();
    }

    public static List<AuditoriumDto> from(List<Auditorium> auditoriums) {
        return auditoriums.stream()
                .map(AuditoriumDto::from)
                .collect(Collectors.toList());
    }

}
