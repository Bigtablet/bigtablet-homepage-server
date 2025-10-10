package com.bigtablet.bigtablethompageserver.domain.user.application.service;

import com.bigtablet.bigtablethompageserver.domain.user.application.response.UserResponse;
import com.bigtablet.bigtablethompageserver.domain.user.client.dto.User;
import com.bigtablet.bigtablethompageserver.domain.user.domain.entity.UserEntity;

public interface UserService {

    void save(UserEntity entity);

    UserResponse getUser();

    User getUser(String email);

    void checkUserEmail(String email);

}
