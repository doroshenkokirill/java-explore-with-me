package ru.practicum.exeptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse errorBadRequest(final BadRequestException e) {
        log.debug("Получен статус 400 Bad request {}", e.getMessage(), e);
        return new ErrorResponse(HttpStatus.BAD_REQUEST, "Incorrectly made request.", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse errorNotFound(final NotFoundException e) {
        log.debug("Получен статус 404 Not found {}", e.getMessage(), e);
        return new ErrorResponse(HttpStatus.NOT_FOUND, "The required object was not found.", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse errorNotUnique(final NotUniqueException e) {
        log.debug("Получен статус 409 Not Unique {}", e.getMessage(), e);
        return new ErrorResponse(HttpStatus.CONFLICT, "Integrity constraint has been violated.", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse errorThrowable(final Throwable e) {
        log.debug("Получен статус 500 Internal error {}. ", e.getMessage(), e);
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected server error.", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse errorConflict(final ConflictException e) {
        log.debug("Получен статус 409 Conflict {}", e.getMessage(), e);
        return new ErrorResponse(HttpStatus.CONFLICT, "Integrity constraint has been violated.", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse errorMethodArgumentNotValid(final MethodArgumentNotValidException e) {
        log.debug("Получен статус 400 Argument not valid {}", e.getMessage(), e);
        return new ErrorResponse(HttpStatus.BAD_REQUEST, "Invalid request parameters.", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse errorMissingServletRequestParameter(final MissingServletRequestParameterException e) {
        log.debug("Получен статус 400 Missing Servlet request parameter {}", e.getMessage(), e);
        return new ErrorResponse(HttpStatus.BAD_REQUEST, "Missing required request parameter.", e.getMessage());
    }
}