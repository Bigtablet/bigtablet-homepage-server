package com.bigtablet.bigtablethompageserver.global.config.web;

import com.bigtablet.bigtablethompageserver.global.infra.filter.RequestLoggingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    @Bean
    public RequestLoggingFilter requestLoggingFilter() {
        return new RequestLoggingFilter();
    }

}
