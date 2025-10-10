package com.bigtablet.bigtablethompageserver.global.common.repository.user.impl;

import com.bigtablet.bigtablethompageserver.domain.user.client.dto.User;
import com.bigtablet.bigtablethompageserver.global.common.repository.user.UserSecurity;
import com.bigtablet.bigtablethompageserver.global.security.auth.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

@Repository
public class UserSecurityImpl implements UserSecurity {

    @Override
    public User getUser() {
        return ((CustomUserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal())
                .getUser();
    }

}
