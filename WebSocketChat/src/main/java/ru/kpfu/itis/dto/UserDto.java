package ru.kpfu.itis.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.models.Message;
import ru.kpfu.itis.models.User;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto implements Serializable {
    private Long id;
    private String email;
    private String name;
    private List<MessageDto> messages;

    public static UserDto from(User user) {
        return UserDto.builder()
                .email(user.getEmail())
                .id(user.getId())
                .name(user.getName())
                .messages(MessageDto.from(user.getMessages()))
                .build();
    }

    public static List<UserDto> from(List<User> users) {
        return users.stream()
                .map(UserDto::from)
                .collect(Collectors.toList());
    }
}
