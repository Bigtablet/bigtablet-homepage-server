package com.bigtablet.bigtablethompageserver.global.common.repository.user;

import com.bigtablet.bigtablethompageserver.domain.user.domain.model.User;

public interface UserSecurity {

    /**
     * 현재 인증된 유저 정보 조회
     * @return User 인증된 유저 도메인 객체
     */
    User getUser();

}
