package com.rk.social_media.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> otherExceptionHandler(Exception e , WebRequest req){
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage(e.getMessage());
        errorDetails.setLocalDateTime(LocalDateTime.now());
        errorDetails.setError(req.getDescription(false));
        return new ResponseEntity<>(errorDetails , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorDetails> UserExceptionHandler(Exception e , WebRequest req){
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage(e.getMessage());
        errorDetails.setLocalDateTime(LocalDateTime.now());
        errorDetails.setError(req.getDescription(false));
        return new ResponseEntity<>(errorDetails , HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(ChatException.class)
    public ResponseEntity<ErrorDetails> ChatExceptionHandler(Exception e , WebRequest req){
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage(e.getMessage());
        errorDetails.setLocalDateTime(LocalDateTime.now());
        errorDetails.setError(req.getDescription(false));
        return new ResponseEntity<>(errorDetails , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CommentException.class)
    public ResponseEntity<ErrorDetails> CommentExceptionHandler(Exception e , WebRequest req){
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage(e.getMessage());
        errorDetails.setLocalDateTime(LocalDateTime.now());
        errorDetails.setError(req.getDescription(false));
        return new ResponseEntity<>(errorDetails , HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MessageException.class)
    public ResponseEntity<ErrorDetails> MessageExceptionHandler(Exception e , WebRequest req){
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage(e.getMessage());
        errorDetails.setLocalDateTime(LocalDateTime.now());
        errorDetails.setError(req.getDescription(false));
        return new ResponseEntity<>(errorDetails , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotificationException.class)
    public ResponseEntity<ErrorDetails> NotificationExceptionHandler(Exception e , WebRequest req){
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage(e.getMessage());
        errorDetails.setLocalDateTime(LocalDateTime.now());
        errorDetails.setError(req.getDescription(false));
        return new ResponseEntity<>(errorDetails , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(PostException.class)
    public ResponseEntity<ErrorDetails> PostExceptionHandler(Exception e , WebRequest req){
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage(e.getMessage());
        errorDetails.setLocalDateTime(LocalDateTime.now());
        errorDetails.setError(req.getDescription(false));
        return new ResponseEntity<>(errorDetails , HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReelException.class)
    public ResponseEntity<ErrorDetails> ReelExceptionHandler(Exception e , WebRequest req){
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage(e.getMessage());
        errorDetails.setLocalDateTime(LocalDateTime.now());
        errorDetails.setError(req.getDescription(false));
        return new ResponseEntity<>(errorDetails , HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StoryException.class)
    public ResponseEntity<ErrorDetails> StoryExceptionHandler(Exception e , WebRequest req){
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage(e.getMessage());
        errorDetails.setLocalDateTime(LocalDateTime.now());
        errorDetails.setError(req.getDescription(false));
        return new ResponseEntity<>(errorDetails , HttpStatus.NOT_FOUND);
    }


}
