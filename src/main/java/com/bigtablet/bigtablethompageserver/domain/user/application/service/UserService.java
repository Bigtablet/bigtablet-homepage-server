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

    /**
     * 신규 유저 저장
     * @param email String 유저 이메일
     * @param password String 암호화된 비밀번호
     * @param name String 유저 이름
     * @return void
     */
    public void save(String email, String password, String name) {
        log.info("[UserService] save - email={}", email);
        userJpaRepository.save(UserEntity.builder()
                .email(email)
                .password(password)
                .name(name)
                .build());
    }

}
