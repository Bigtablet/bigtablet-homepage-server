package com.bigtablet.bigtablethompageserver.domain.admin.application.query;

import com.bigtablet.bigtablethompageserver.domain.admin.domain.repository.jpa.WebAuthnJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WebAuthnQueryService {

    private final WebAuthnJpaRepository webAuthnJpaRepository;

    /**
     * 어드민에게 등록된 크레덴셜 존재 여부 확인
     * @param adminId 어드민 ID
     * @return boolean 존재 시 true
     */
    public boolean hasCredential(String adminId) {
        return webAuthnJpaRepository.existsByAdminId(adminId);
    }

}
