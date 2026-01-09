package com.bigtablet.bigtablethompageserver.domain.user.client.api;

import com.bigtablet.bigtablethompageserver.domain.user.application.response.UserResponse;
import com.bigtablet.bigtablethompageserver.domain.user.application.service.UserService;
import com.bigtablet.bigtablethompageserver.global.common.dto.response.BaseResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserApiHandler {

    private final UserService userService;

    /**
     * 내 정보 조회(토큰기반) API
     *
     * @return status, message, data { UserResponse }
     *
     * */
    @GetMapping
    public BaseResponseData<UserResponse> getUserByToken() {
        return BaseResponseData.ok(
                "조회 성공",
                UserResponse.of(userService.getUser()));
    }

}
