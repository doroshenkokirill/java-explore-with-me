package ru.practicum.comments.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comments.service.AdminCommentService;

@RestController
@RequestMapping(path = "/admin/comments")
@RequiredArgsConstructor
public class AdminCommentController {
    private final AdminCommentService adminCommentService;

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable int id) {
        adminCommentService.deleteComment(id);
    }
}
