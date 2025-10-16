package com.bigtablet.bigtablethompageserver.global.infra.email.service;

public interface EmailService {

    void sendNoReply(String to, String subject, String content);

    void sendRecruit(String to, String subject, String content);

}
