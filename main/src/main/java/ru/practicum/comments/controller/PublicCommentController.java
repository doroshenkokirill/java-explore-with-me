package ru.practicum.comments.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comments.dto.CommentDto;
import ru.practicum.comments.service.PublicCommentService;

import java.util.List;

@RestController
@RequestMapping(path = "/admin/comments")
@RequiredArgsConstructor
public class PublicCommentController {
    private final PublicCommentService publicCommentService;

    @GetMapping("/events/{eventId}/comments")
    public List<CommentDto> getAllComments(@PathVariable int eventId,
                                           @RequestParam(defaultValue = "0") int from,
                                           @RequestParam(defaultValue = "10") int size) {
        return publicCommentService.getAllComments(eventId, from, size);
    }
}
