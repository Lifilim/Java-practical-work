package org.example.exceptions;

public class DatabaseSelectException extends RuntimeException {
  String message = "it's too hard to select :((";

  @Override
  public String getMessage() { return message; }

  public DatabaseSelectException(Exception e) { this.message = this.message + "\n    ^ More specifically: " + e.getMessage();}
}
