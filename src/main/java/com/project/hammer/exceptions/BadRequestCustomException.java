package com.project.hammer.exceptions;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

public class BadRequestCustomException extends RuntimeException {

       public BadRequestCustomException(String message){
           super(message);
       }
}
