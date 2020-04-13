package ru.kpfu.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.models.Message;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDto {
    private Long id;
    private Long userId;
    private String text;

    public static MessageDto from(Message message) {
        return MessageDto.builder()
                .userId(message.getUser().getId())
                .id(message.getId())
                .text(message.getText())
                .build();
    }

    public static List<MessageDto> from(List<Message> messages) {
        return messages.stream()
                .map(MessageDto::from)
                .collect(Collectors.toList());
    }
}
