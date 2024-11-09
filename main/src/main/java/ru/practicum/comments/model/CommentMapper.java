package ru.practicum.comments.model;

import lombok.experimental.UtilityClass;
import ru.practicum.comments.dto.CommentDto;
import ru.practicum.events.model.Event;
import ru.practicum.users.model.User;

@UtilityClass
public class CommentMapper {
    public CommentDto toCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .authorId(comment.getAuthor().getId())
                .eventId(comment.getEvent().getId())
                .date(comment.getCommentDate())
                .edited(comment.isEdited())
                .build();
    }

    public Comment toComment(CommentDto commentDto, User author, Event event) {
        return Comment.builder()
                .id(commentDto.getId())
                .text(commentDto.getText())
                .author(author)
                .event(event)
                .commentDate(commentDto.getDate())
                .edited(commentDto.isEdited())
                .build();
    }
}
