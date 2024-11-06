package ru.practicum.comments.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.comments.service.AdminCommentService;

@RestController
@RequestMapping(path = "/admin/comments")
@RequiredArgsConstructor
public class AdminCommentController {
    private final AdminCommentService adminCommentService;

    @DeleteMapping(path = "/{id}")
    public void deleteComment(@PathVariable int id) {
        adminCommentService.deleteComment(id);
    }
}
