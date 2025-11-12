package com.bigtablet.bigtablethompageserver.domain.user.application.service;

import com.bigtablet.bigtablethompageserver.domain.user.domain.model.User;
import com.bigtablet.bigtablethompageserver.domain.user.exception.UserAlreadyExistException;
import com.bigtablet.bigtablethompageserver.domain.user.exception.UserNotFoundException;
import com.bigtablet.bigtablethompageserver.domain.user.domain.entity.UserEntity;
import com.bigtablet.bigtablethompageserver.domain.user.domain.repository.jpa.UserJpaRepository;
import com.bigtablet.bigtablethompageserver.global.common.repository.user.UserSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserJpaRepository userJpaRepository;
    private final UserSecurity userSecurity;

    public void save(String email, String password, String name) {
        userJpaRepository.save(UserEntity.builder()
                .email(email)
                .password(password)
                .name(name)
                .build());
    }

    public User getUser() {
        return userSecurity.getUser();
    }

    public User getUser(String email) {
        return userJpaRepository
                .findByEmail(email)
                .map(User::of)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);
    }

    public void checkUserEmail(String email) {
        if (userJpaRepository.findByEmail(email).isPresent()) {
            throw UserAlreadyExistException.EXCEPTION;
        }
    }

}
