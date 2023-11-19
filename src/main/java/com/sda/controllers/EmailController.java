package com.sda.controllers;

import com.sda.services.EmailException;
import com.sda.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send-email")
    public ResponseEntity<String> sendEmail(
            @RequestParam String to,
            @RequestParam String subject,
            @RequestParam String body,
            @RequestParam(required = false) String attachmentPath) {

        try {
            emailService.sendEmail(to, subject, body, attachmentPath);
            return ResponseEntity.ok("Email sent successfully");
        } catch (EmailException e) {
            return ResponseEntity.status(500).body("Failed to send email");
        }
    }
}
