package com.sda.rest;


import org.springframework.http.ResponseEntity;


public interface EmailRest {
    ResponseEntity<String> sendEmail(String to, String subject, String text, String attachementPath);

}




















