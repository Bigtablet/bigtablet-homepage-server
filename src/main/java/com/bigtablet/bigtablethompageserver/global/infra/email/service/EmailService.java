package com.bigtablet.bigtablethompageserver.global.infra.email.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender noreplyMailSender;
    private final JavaMailSender recruitMailSender;

    /**
     * 이메일 서비스 생성자 (noreply, recruit 발신자 주입)
     * @param noreplyMailSender JavaMailSender noreply 계정 메일 발신자
     * @param recruitMailSender JavaMailSender recruit 계정 메일 발신자
     */
    public EmailService(
            @Qualifier("noreplyMailSender") JavaMailSender noreplyMailSender,
            @Qualifier("recruitMailSender") JavaMailSender recruitMailSender
    ) {
        this.noreplyMailSender = noreplyMailSender;
        this.recruitMailSender = recruitMailSender;
    }

    /**
     * noreply 계정으로 이메일 비동기 발송
     * @param to String 수신자 이메일 주소
     * @param subject String 이메일 제목
     * @param content String 이메일 내용 (HTML)
     */
    @Async
    public void sendNoReply(String to, String subject, String content) {
        send(noreplyMailSender, "noreply@bigtablet.com", to, subject, content);
    }

    /**
     * recruit 계정으로 이메일 비동기 발송
     * @param to String 수신자 이메일 주소
     * @param subject String 이메일 제목
     * @param content String 이메일 내용 (HTML)
     */
    @Async
    public void sendRecruit(String to, String subject, String content) {
        send(recruitMailSender, "recruit@bigtablet.com", to, subject, content);
    }

    /**
     * 이메일 발송 공통 로직
     * @param sender JavaMailSender 메일 발신 객체
     * @param from String 발신자 이메일 주소
     * @param to String 수신자 이메일 주소
     * @param subject String 이메일 제목
     * @param content String 이메일 내용 (HTML)
     */
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