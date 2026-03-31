package com.bigtablet.bigtablethompageserver.global.security.auth;

import com.bigtablet.bigtablethompageserver.domain.user.domain.model.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
public class CustomUserDetails implements UserDetails {

    private final User user;

    /**
     * CustomUserDetails 생성자
     * @param user User 유저 도메인 객체
     */
    public CustomUserDetails(final User user) {
        this.user = user;
    }

    /**
     * 사용자 권한 목록 반환
     * @return Collection 권한 목록
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    /**
     * 비밀번호 반환 (사용하지 않음)
     * @return String 비밀번호
     */
    @Override
    public String getPassword() {
        return null;
    }

    /**
     * 사용자 이메일 반환
     * @return String 사용자 이메일
     */
    @Override
    public String getUsername() {
        return user.email();
    }

}
