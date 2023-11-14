package com.sda.serviceImpl;

public class EmailException extends Exception {
    public EmailException(String message, Exception ex) {
        super(message, ex);
    }
}
