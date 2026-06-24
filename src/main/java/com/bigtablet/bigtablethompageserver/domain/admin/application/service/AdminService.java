package com.bigtablet.bigtablethompageserver.domain.admin.application.service;

import com.bigtablet.bigtablethompageserver.domain.admin.domain.entity.AdminEntity;
import com.bigtablet.bigtablethompageserver.domain.admin.domain.enums.AdminRole;
import com.bigtablet.bigtablethompageserver.domain.admin.domain.repository.jpa.AdminJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminJpaRepository adminJpaRepository;

    /**
     * 신규 어드민 저장 (UUID 자동 생성, 권한은 ADMIN 고정)
     * @param email 어드민 이메일
     * @return String 생성된 어드민 ID
     */
    @Transactional
    public String save(String email) {
        String id = UUID.randomUUID().toString();
        log.info("[AdminService] save - id={}, email={}", id, email);
        adminJpaRepository.save(AdminEntity.builder()
                .id(id)
                .email(email)
                .role(AdminRole.ADMIN)
                .build());
        return id;
    }

}
