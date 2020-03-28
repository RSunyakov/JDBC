package ru.springuser.dto.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.springuser.dto.UserDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseUserDto {
    private UserDto data;
}
