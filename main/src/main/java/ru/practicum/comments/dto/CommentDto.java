package ru.practicum.comments.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class CommentDto {
    private int id;
    @NotBlank(message = "Field text is blank")
    private String text;
    private int authorId;
    private int eventId;
    private LocalDateTime date;
    private boolean edited;
}
