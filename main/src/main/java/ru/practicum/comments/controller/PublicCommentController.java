package ru.practicum.comments.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comments.dto.CommentDto;
import ru.practicum.comments.service.PublicCommentService;

import java.util.List;

@RestController
@RequestMapping(path = "/events/")
@RequiredArgsConstructor
public class PublicCommentController {
    private final PublicCommentService publicCommentService;

    @GetMapping("/{eventId}/comments")
    public List<CommentDto> getComments(@PathVariable int eventId,
                                           @RequestParam(defaultValue = "0") int from,
                                           @RequestParam(defaultValue = "10") int size) {
        return publicCommentService.getComments(eventId, from, size);
    }

    @GetMapping("/comments")
    public List<CommentDto> getAllComments(
                                        @RequestParam(defaultValue = "0") int from,
                                        @RequestParam(defaultValue = "10") int size) {
        return publicCommentService.getAllComments(from, size);
    }
}
