package com.Enquirenow.utility;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public boolean sendEmail(String to, String subject, String body) {
        boolean isSent = false;
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true); // true indicates multipart message

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true); // true indicates HTML content
            mailSender.send(mimeMessage);
            isSent = true;
        } catch (MessagingException e) {
            e.printStackTrace();
            // Handle messaging exceptions appropriately
        } catch (Exception e) {
            e.printStackTrace();
            // Handle other exceptions appropriately
        }
        return isSent;
    }
}


