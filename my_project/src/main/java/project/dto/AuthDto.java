package project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.models.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthDto {

    private String email;
    private String name;

    public static AuthDto fromUser(User user){
        return AuthDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
}
