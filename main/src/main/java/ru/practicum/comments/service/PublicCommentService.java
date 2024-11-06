package ru.practicum.comments.service;

import ru.practicum.comments.dto.CommentDto;

import java.util.List;

public interface PublicCommentService {
    List<CommentDto> getAllComments(int eventId, int from, int size);
}
