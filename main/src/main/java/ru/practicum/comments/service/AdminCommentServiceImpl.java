package ru.practicum.comments.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.comments.repository.CommentRepository;
import ru.practicum.exeptions.NotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminCommentServiceImpl implements AdminCommentService {
    private final CommentRepository commentRepository;

    @Override
    public void deleteComment(int id) {
        if (!commentRepository.existsById(id)) {
            throw new NotFoundException("Comment with id " + id + " dont exists");
        }
        commentRepository.deleteById(id);
        log.info("Deleted comment with id {}", id);
    }
}
