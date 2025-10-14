package com.bigtablet.bigtablethompageserver.domain.user.client.api;

import com.bigtablet.bigtablethompageserver.domain.user.application.response.UserResponse;
import com.bigtablet.bigtablethompageserver.domain.user.application.service.UserService;
import com.bigtablet.bigtablethompageserver.global.common.dto.response.BaseResponseData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(name = "유저", description = "유저 API")
public class UserApiHandler {

    public final UserService userService;

    /**
     * 내 정보 조회(토큰기반) API
     *
     * @return status, message, data { UserResponse }
     *
     * */
    @GetMapping
    @Operation(summary = "유저 정보 토큰 기반 조회", description = "토큰을 기반으로 유저 정보를 조회합니다")
    public BaseResponseData<UserResponse> getUserByToken() {
        return BaseResponseData.ok(
                "조회 성공",
                userService.getUser());
    }

}
