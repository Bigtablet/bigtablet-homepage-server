package com.bigtablet.bigtablethompageserver.global.config.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateConfig {

    private static final Duration CONNECT_TIMEOUT = Duration.ofSeconds(3);
    private static final Duration READ_TIMEOUT = Duration.ofSeconds(5);

    /**
     * Slack webhook 호출용 RestTemplate.
     * connect/read timeout을 명시하여 외부 API 지연 시 호출 스레드(@Async 포함)가 무한 대기하지 않도록 한다.
     * Bean 이름을 용도별로 스코프(`slackRestTemplate`)해 향후 다른 RestTemplate 추가 시 주입 충돌을 회피.
     * @return RestTemplate 타임아웃 적용된 RestTemplate
     */
    @Bean
    public RestTemplate slackRestTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(CONNECT_TIMEOUT);
        factory.setReadTimeout(READ_TIMEOUT);
        return new RestTemplate(factory);
    }

}
