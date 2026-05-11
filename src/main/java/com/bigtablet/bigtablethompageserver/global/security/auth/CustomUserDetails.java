package com.bigtablet.bigtablethompageserver.global.security.auth;

import com.bigtablet.bigtablethompageserver.domain.admin.domain.model.Admin;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class CustomUserDetails implements UserDetails {

    private final Admin admin;

    /**
     * CustomUserDetails 생성자
     * @param admin Admin 어드민 도메인 객체
     */
    public CustomUserDetails(final Admin admin) {
        this.admin = admin;
    }

    /**
     * 어드민 권한 목록 반환 (ROLE_ 접두어 부여)
     * @return Collection 권한 목록
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + admin.role().name()));
    }

    /**
     * 비밀번호 반환 (WebAuthn 사용으로 미사용)
     * @return String 비밀번호 (항상 null)
     */
    @Override
    public String getPassword() {
        return null;
    }

    /**
     * 어드민 식별자(=Admin ID) 반환 — JWT subject와 일치
     * @return String 어드민 ID
     */
    @Override
    public String getUsername() {
        return admin.id();
    }

}
