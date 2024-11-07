package ru.practicum.comments.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comments.dto.CommentDto;
import ru.practicum.comments.service.PrivateCommentService;

@RestController
@RequestMapping(path = "/events/{eventId}/comments")
@RequiredArgsConstructor
public class PrivateCommentController {
    private final PrivateCommentService privateCommentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto createComment(@RequestParam int userId,
                                    @PathVariable int eventId,
                                    @RequestBody @Valid CommentDto commentDto) {
        return privateCommentService.createComment(userId, eventId, commentDto);
    }

    @PatchMapping("/{commentId}")
    public CommentDto updateComment(@PathVariable int eventId,
                                    @PathVariable int commentId,
                                    @RequestParam int userId,
                                    @RequestBody @Valid CommentDto commentDto) {
        return privateCommentService.updateComment(eventId, commentId, userId, commentDto);
    }
}
