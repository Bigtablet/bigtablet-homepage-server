package com.bigtablet.bigtablethompageserver.global.infra.email.service.impl;

import com.bigtablet.bigtablethompageserver.global.infra.email.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender noreplyMailSender;
    private final JavaMailSender recruitMailSender;

    public EmailServiceImpl(
            @Qualifier("noreplyMailSender") JavaMailSender noreplyMailSender,
            @Qualifier("recruitMailSender") JavaMailSender recruitMailSender
    ) {
        this.noreplyMailSender = noreplyMailSender;
        this.recruitMailSender = recruitMailSender;
    }

    @Override
    public void sendNoReply(String to, String subject, String content) {
        send(noreplyMailSender, "noreply@bigtablet.com", to, subject, content);
    }

    @Override
    public void sendRecruit(String to, String subject, String content) {
        send(recruitMailSender, "recruit@bigtablet.com", to, subject, content);
    }

    public void send(JavaMailSender sender, String from, String to, String subject, String content) {
        MimeMessage message = sender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            sender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("메일 전송 실패", e);
        }
    }

}