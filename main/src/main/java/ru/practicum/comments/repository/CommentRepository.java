package ru.practicum.comments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.comments.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Comment findById(int id);
}
