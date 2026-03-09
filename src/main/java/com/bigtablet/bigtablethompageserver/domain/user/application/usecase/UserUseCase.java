package com.bigtablet.bigtablethompageserver.domain.user.application.usecase;

import com.bigtablet.bigtablethompageserver.domain.user.application.query.UserQueryService;
import com.bigtablet.bigtablethompageserver.domain.user.application.response.UserResponse;
import com.bigtablet.bigtablethompageserver.domain.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserUseCase {

    private final UserQueryService userQueryService;

    public UserResponse get() {
        log.info("[UserUseCase] get - by token");
        User user = userQueryService.find();
        return UserResponse.of(user);
    }

}
