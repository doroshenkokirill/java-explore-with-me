package ru.practicum.comments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.comments.model.Comment;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Comment findById(int id);

    List<Comment> findAllByEventId(int eventId, Pageable pageable);
}
