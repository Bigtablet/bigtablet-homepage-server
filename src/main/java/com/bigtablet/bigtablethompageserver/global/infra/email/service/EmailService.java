package com.bigtablet.bigtablethompageserver.global.infra.email.service;

public interface EmailService {

    void send(String email, String toMail, String title, String content);

}
