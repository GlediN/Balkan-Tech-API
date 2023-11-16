package com.sda.restImpl;

import com.sda.rest.EmailRest;
import com.sda.service.EmailService;
import com.sda.serviceImpl.EmailException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class EmailRestImpl implements EmailRest {

    private final EmailService emailService;

        public EmailRestImpl(EmailService emailService) {
            this.emailService = emailService;
        }

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




