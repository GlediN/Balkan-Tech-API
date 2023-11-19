package com.sda.services;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    public void sendEmail(String to, String subject, String text, String attachmentPath) throws EmailException {
        try {

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            if (attachmentPath != null) {
                FileSystemResource file = new FileSystemResource(attachmentPath);
                helper.addAttachment(file.getFilename(), file);


            }

            javaMailSender.send(message);
        } catch (Exception e) {
            throw new EmailException("Fail to send email", e);
        }
    }
}
