package org.example.exceptions;

public class DatabaseSelectException extends RuntimeException {
  @Override
  public String getMessage() {
    return "it's too hard to select :((";
  }
}
