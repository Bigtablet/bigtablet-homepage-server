package com.bigtablet.bigtablethompageserver.domain.user.client.api;

import com.bigtablet.bigtablethompageserver.domain.user.application.response.UserResponse;
import com.bigtablet.bigtablethompageserver.domain.user.application.service.UserService;
import com.bigtablet.bigtablethompageserver.domain.user.domain.model.User;
import com.bigtablet.bigtablethompageserver.global.common.annotation.RestApiHandler;
import com.bigtablet.bigtablethompageserver.global.common.dto.response.BaseResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

@Validated
@RequiredArgsConstructor
@RestApiHandler("/user")
public class UserApiHandler {

    public final UserService userService;

    /**
     * 내 정보 조회(토큰기반) API
     *
     * @return status, message, data { UserResponse }
     *
     * */
    @GetMapping
    public BaseResponseData<UserResponse> getUserByToken() {
        User user = userService.getUser();
        return BaseResponseData.ok(
                "조회 성공",
                UserResponse.of(user));
    }

}
