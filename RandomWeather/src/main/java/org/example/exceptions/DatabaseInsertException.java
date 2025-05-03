package org.example.exceptions;

public class DatabaseInsertException extends RuntimeException {
    @Override
    public String getMessage() {
        return "it's too hard to insert...";
    }
}
