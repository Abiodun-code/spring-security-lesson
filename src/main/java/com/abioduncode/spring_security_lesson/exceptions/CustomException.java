package com.abioduncode.spring_security_lesson.exceptions;

public class CustomException extends RuntimeException {
  public CustomException(String message){
    super(message);
  }
}
