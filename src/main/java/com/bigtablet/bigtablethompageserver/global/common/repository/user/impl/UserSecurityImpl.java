package com.bigtablet.bigtablethompageserver.global.common.repository.user.impl;

import com.bigtablet.bigtablethompageserver.domain.user.domain.model.User;
import com.bigtablet.bigtablethompageserver.global.common.repository.user.UserSecurity;
import com.bigtablet.bigtablethompageserver.global.security.auth.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

@Repository
public class UserSecurityImpl implements UserSecurity {

    /**
     * SecurityContext에서 현재 인증된 유저 정보 조회
     * @return User 인증된 유저 도메인 객체
     */
    @Override
    public User getUser() {
        return ((CustomUserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal())
                .getUser();
    }

}
