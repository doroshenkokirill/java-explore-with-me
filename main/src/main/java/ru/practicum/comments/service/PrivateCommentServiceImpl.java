package ru.practicum.comments.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.practicum.comments.dto.CommentDto;
import ru.practicum.comments.model.Comment;
import ru.practicum.comments.model.CommentMapper;
import ru.practicum.comments.repository.CommentRepository;
import ru.practicum.events.model.Event;
import ru.practicum.events.model.enums.EventState;
import ru.practicum.events.repository.EventRepository;
import ru.practicum.exeptions.ConflictException;
import ru.practicum.exeptions.NotFoundException;
import ru.practicum.users.model.User;
import ru.practicum.users.repository.UserRepository;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrivateCommentServiceImpl implements PrivateCommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public CommentDto createComment(int userId, int eventId, CommentDto commentDto) {
        checkId(userId, userRepository);
        checkId(eventId, eventRepository);
        Event event = eventRepository.getEventById(eventId);
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new ConflictException("Event is not published");
        }

        commentDto.setDate(LocalDateTime.now());
        User user = userRepository.getUserById(userId);
        Comment comment = CommentMapper.toComment(commentDto, user, event);

        CommentDto result = CommentMapper.toCommentDto(commentRepository.save(comment));
        log.info("Created comment {}", comment);
        return result;
    }

    @Override
    public CommentDto updateComment(int commentId, int userId, int eventId, CommentDto commentDto) {
        checkId(commentId, commentRepository);
        checkId(userId, userRepository);
        checkId(eventId, eventRepository);
        if (commentDto.getAuthorId() != userId) {
            throw new ConflictException("User can`t update comment");
        }

        Comment commentToUpdate = commentRepository.findById(commentId);
        commentToUpdate.setText(commentDto.getText());
        commentToUpdate.setCommentDate(LocalDateTime.now());
        commentToUpdate.setEdited(true);

        CommentDto result = CommentMapper.toCommentDto(commentRepository.save(commentToUpdate));
        log.info("Updated comment {}", result);
        return result;
    }

    private void checkId(int id, JpaRepository<?, Integer> repository) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Not found with id " + id + " from: " + repository);
        }
    }
}
