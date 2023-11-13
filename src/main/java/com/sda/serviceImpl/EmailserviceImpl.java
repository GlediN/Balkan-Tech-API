package com.sda.serviceImpl;


import com.sda.service.Emailservice;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
@RequiredArgsConstructor
public class EmailserviceImpl implements Emailservice{


    private final JavaMailSender javaMailSender;


    public void sendEmailservice(String to, String subject, String text, String attachmentPath) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            File attachment = new File(attachmentPath);
            helper.addAttachment(attachment.getName(), attachment);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();

        }
    }
}
