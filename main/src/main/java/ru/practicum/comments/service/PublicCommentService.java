package ru.practicum.comments.service;

import ru.practicum.comments.dto.CommentDto;

import java.util.List;

public interface PublicCommentService {
    List<CommentDto> getComments(int eventId, int from, int size);

    List<CommentDto> getAllComments(int from, int size);
}
