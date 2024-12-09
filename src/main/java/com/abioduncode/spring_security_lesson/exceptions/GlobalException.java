package com.abioduncode.spring_security_lesson.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;

@RestControllerAdvice
public class GlobalException{

  // Handle CustomException
  @ExceptionHandler(CustomException.class)
  public ResponseEntity<?> handleCustomException(CustomException ex){
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }
  
  // Handle Generic Exceptions (Optional)
  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handleGenericException(Exception ex){
    return new ResponseEntity<>("Something went wrong: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }  
}
