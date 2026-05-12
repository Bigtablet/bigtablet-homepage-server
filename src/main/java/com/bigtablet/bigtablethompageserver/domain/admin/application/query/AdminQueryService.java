package com.bigtablet.bigtablethompageserver.domain.admin.application.query;

import com.bigtablet.bigtablethompageserver.domain.admin.domain.model.Admin;
import com.bigtablet.bigtablethompageserver.domain.admin.domain.repository.jpa.AdminJpaRepository;
import com.bigtablet.bigtablethompageserver.domain.admin.exception.AdminNotFoundException;
import com.bigtablet.bigtablethompageserver.domain.admin.exception.InvalidEmailDomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminQueryService {

    // 허용된 어드민 이메일 도메인 — bigtablet.com 정확 매치만 통과 (서브도메인 차단)
    private static final String ALLOWED_EMAIL_DOMAIN = "bigtablet.com";

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

    /**
     * 어드민 이메일 도메인 검증 (서브도메인 차단, 정확 매치만 통과)
     * @param email String 어드민 이메일 (null 허용 — null이면 도메인 불일치 처리)
     * @return void (도메인 불일치 시 예외)
     */
    public void checkEmailDomain(String email) {
        if (email == null) {
            throw InvalidEmailDomainException.EXCEPTION;
        }
        // split("@", -1)로 트레일링 빈 토큰 보존 — "user@bigtablet.com@" 같은 비정상 입력 차단
        // parts[0] blank 체크 — "@bigtablet.com" / " @bigtablet.com" 같이 로컬 파트가 비거나 공백뿐인 입력 차단
        String[] parts = email.split("@", -1);
        if (parts.length != 2 || parts[0].isBlank() || !parts[1].equalsIgnoreCase(ALLOWED_EMAIL_DOMAIN)) {
            throw InvalidEmailDomainException.EXCEPTION;
        }
    }

}
