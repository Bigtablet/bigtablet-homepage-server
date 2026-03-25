package com.bigtablet.bigtablethompageserver.domain.user.application.query;

import com.bigtablet.bigtablethompageserver.domain.user.domain.model.User;
import com.bigtablet.bigtablethompageserver.domain.user.domain.repository.jpa.UserJpaRepository;
import com.bigtablet.bigtablethompageserver.domain.user.exception.UserAlreadyExistException;
import com.bigtablet.bigtablethompageserver.domain.user.exception.UserNotFoundException;
import com.bigtablet.bigtablethompageserver.global.common.repository.user.UserSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserQueryService {

    private final UserJpaRepository userJpaRepository;
    private final UserSecurity userSecurity;

    /**
     * 현재 로그인한 유저 조회 (SecurityContext 기반)
     * @return User 유저 도메인 객체
     */
    public User find() {
        return userSecurity.getUser();
    }

    /**
     * 이메일로 유저 조회
     * @param email String 유저 이메일
     * @return User 유저 도메인 객체
     */
    public User find(String email) {
        return userJpaRepository
                .findByEmail(email)
                .map(User::of)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);
    }

    /**
     * 이메일 중복 검증 (중복 시 예외 발생)
     * @param email String 검증할 이메일 주소
     * @return void
     */
    public void checkEmailExists(String email) {
        if (userJpaRepository.findByEmail(email).isPresent()) {
            throw UserAlreadyExistException.EXCEPTION;
        }
    }

}
