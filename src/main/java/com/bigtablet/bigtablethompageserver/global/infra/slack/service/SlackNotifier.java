package com.bigtablet.bigtablethompageserver.global.infra.slack.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SlackNotifier {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${slack.webhook-url}")
    private String webhookUrl;

    @Async
    public void sendApplicantNotification(String jobTitle, String applicantName, Long recruitIdx) {
        String detailUrl = "https://admin.bigtablet.com/recruit/applicant/" + recruitIdx;
        Map<String, Object> body = new HashMap<>();
        List<Map<String, Object>> blocks = new ArrayList<>();
        Map<String, Object> section1 = new HashMap<>();
        section1.put("type", "section");
        section1.put("text", Map.of(
                "type", "mrkdwn",
                "text", "*새 지원자가 등록되었습니다* :tada:"
        ));
        blocks.add(section1);
        Map<String, Object> section2 = new HashMap<>();
        section2.put("type", "section");
        section2.put("fields", List.of(
                Map.of(
                        "type", "mrkdwn",
                        "text", "*공고 제목:*\n" + jobTitle
                ),
                Map.of(
                        "type", "mrkdwn",
                        "text", "*지원자 이름:*\n" + applicantName
                ),
                Map.of(
                        "type", "mrkdwn",
                        "text", "*상세보기:*\n<" + detailUrl + "|지원자 상세 보기>"
                )
        ));
        blocks.add(section2);
        body.put("blocks", blocks);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        restTemplate.postForEntity(webhookUrl, entity, String.class);
    }

}

