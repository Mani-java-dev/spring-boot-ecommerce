package com.project.hammer.exceptions;

import com.project.hammer.constants.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GradianExceptionHandler {

    @ExceptionHandler(BadRequestCustomException.class)
    public ResponseEntity<APIResponse> badRequestHandler(BadRequestCustomException e){
         APIResponse response= new APIResponse();
         response.setMessage(e.getMessage());
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
