package ru.practicum.comments.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.comments.dto.CommentDto;
import ru.practicum.comments.model.Comment;
import ru.practicum.comments.model.CommentMapper;
import ru.practicum.comments.repository.CommentRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PublicCommentServiceImpl implements PublicCommentService {
    private final CommentRepository commentRepository;

    @Override
    public List<CommentDto> getAllComments(int eventId, int from, int size) {
        Pageable pageable = PageRequest.of(from / size, size);
        List<Comment> commentList = commentRepository.findAll(pageable).stream().toList();

        List<CommentDto> result = commentList.stream().map(CommentMapper::toCommentDto).toList();
        log.info("Founded {} comments for eventId {}", result.size(), eventId);
        return result;
    }
}
