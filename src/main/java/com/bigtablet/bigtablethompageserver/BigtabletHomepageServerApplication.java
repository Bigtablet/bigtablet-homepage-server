package com.bigtablet.bigtablethompageserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableJpaAuditing
@SpringBootApplication
public class BigtabletHomepageServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BigtabletHomepageServerApplication.class, args);
    }

}
