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

    public User find() {
        return userSecurity.getUser();
    }

    public User find(String email) {
        return userJpaRepository
                .findByEmail(email)
                .map(User::of)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);
    }

    public void checkEmailExists(String email) {
        if (userJpaRepository.findByEmail(email).isPresent()) {
            throw UserAlreadyExistException.EXCEPTION;
        }
    }

}
