package project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.models.Message;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDto {
    private Long userId;
    private Long receiverId;
    private String text;

    public static MessageDto fromMessage(Message message){
        return MessageDto.builder()
                .text(message.getText())
                .userId(message.getId_from())
                .receiverId(message.getId_to())
                .build();
    }
}
