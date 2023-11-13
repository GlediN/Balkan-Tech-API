package com.sda.rest;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping
public interface EmailRest {


    EmailRest emailService = null;
    @PostMapping("/send")
    public default String sendEmailWithAttachment(
            @RequestParam String to,
            @RequestParam String subject,
            @RequestParam String text,
            @RequestParam String attachmentPath){
        emailService.sendEmailWithAttachment(to, subject, text, attachmentPath);
        return "Email sent with success";

    }



}
















