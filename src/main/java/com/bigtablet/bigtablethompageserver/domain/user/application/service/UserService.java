package com.bigtablet.bigtablethompageserver.domain.user.application.service;

import com.bigtablet.bigtablethompageserver.domain.user.domain.entity.UserEntity;
import com.bigtablet.bigtablethompageserver.domain.user.domain.repository.jpa.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserJpaRepository userJpaRepository;

    public void save(String email, String password, String name) {
        log.info("[UserService] save - email={}", email);
        userJpaRepository.save(UserEntity.builder()
                .email(email)
                .password(password)
                .name(name)
                .build());
    }

}
