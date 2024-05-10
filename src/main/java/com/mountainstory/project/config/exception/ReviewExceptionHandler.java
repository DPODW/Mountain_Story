package com.mountainstory.project.config.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class ReviewExceptionHandler {

    @ExceptionHandler(ReviewException.class)
    @ResponseBody
    public ResponseEntity<String> handleReviewException(Exception ex){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("error");
    }
}
