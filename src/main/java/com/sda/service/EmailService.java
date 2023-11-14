package com.sda.service;


import com.sda.serviceImpl.EmailException;

public interface EmailService {
    void sendEmail(String to, String subject, String text, String attachmentPath) throws EmailException;
}
