package ru.practicum.comments.service;

import ru.practicum.comments.dto.CommentDto;

public interface PrivateCommentService {
    CommentDto createComment(int userId, int eventId, CommentDto commentDto);

    CommentDto updateComment(int commentId, int userId, int eventId, CommentDto commentDto);
}
