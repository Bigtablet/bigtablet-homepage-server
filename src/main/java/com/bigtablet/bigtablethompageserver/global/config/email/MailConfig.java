package com.bigtablet.bigtablethompageserver.global.config.email;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class MailConfig {

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private boolean auth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private boolean starttls;

    @Value("${spring.mail.properties.mail.smtp.connectiontimeout}")
    private int connectionTimeout;

    @Value("${spring.mail.properties.mail.smtp.timeout}")
    private int timeout;

    @Value("${spring.mail.properties.mail.smtp.writetimeout}")
    private int writeTimeout;

    @Value("${spring.mail.email_noreply}")
    private String noreplyEmail;

    @Value("${spring.mail.password_noreply}")
    private String noreplyPassword;

    @Value("${spring.mail.email_recruit}")
    private String recruitEmail;

    @Value("${spring.mail.password_recruit}")
    private String recruitPassword;

    @Bean(name = "noreplyMailSender")
    public JavaMailSender noreplyMailSender() {
        return createMailSender(noreplyEmail, noreplyPassword);
    }

    @Bean(name = "recruitMailSender")
    public JavaMailSender recruitMailSender() {
        return createMailSender(recruitEmail, recruitPassword);
    }

    private JavaMailSender createMailSender(String email, String password) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(email);
        mailSender.setPassword(password);
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", auth);
        props.put("mail.smtp.starttls.enable", starttls);
        props.put("mail.smtp.connectiontimeout", connectionTimeout);
        props.put("mail.smtp.timeout", timeout);
        props.put("mail.smtp.writetimeout", writeTimeout);
        return mailSender;
    }

}
