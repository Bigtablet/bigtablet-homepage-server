package com.bigtablet.bigtablethompageserver.domain.admin.application.query;

import com.bigtablet.bigtablethompageserver.domain.admin.domain.model.Admin;
import com.bigtablet.bigtablethompageserver.domain.admin.domain.repository.jpa.AdminJpaRepository;
import com.bigtablet.bigtablethompageserver.domain.admin.exception.AdminNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminQueryService {

    private final AdminJpaRepository adminJpaRepository;

    /**
     * 이메일로 어드민 조회
     * @param email String 어드민 이메일
     * @return Admin 어드민 도메인 객체
     */
    public Admin findByEmail(String email) {
        return adminJpaRepository.findByEmailIgnoreCase(email)
                .map(Admin::of)
                .orElseThrow(() -> AdminNotFoundException.EXCEPTION);
    }

    /**
     * 이메일로 어드민 조회 (없으면 null 반환 — 동시성 안전한 신규 생성 분기용)
     * @param email String 어드민 이메일
     * @return Admin or null
     */
    public Admin findByEmailOrNull(String email) {
        return adminJpaRepository.findByEmailIgnoreCase(email)
                .map(Admin::of)
                .orElse(null);
    }

    /**
     * ID로 어드민 조회
     * @param id String 어드민 ID
     * @return Admin 어드민 도메인 객체
     */
    public Admin find(String id) {
        return adminJpaRepository.findById(id)
                .map(Admin::of)
                .orElseThrow(() -> AdminNotFoundException.EXCEPTION);
    }

}
