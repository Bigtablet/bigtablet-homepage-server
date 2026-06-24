package com.bigtablet.bigtablethompageserver.domain.recruit.application.event;

/**
 * 채용 상태 변경 트랜잭션 커밋 후 발송할 메일 정보를 담는 도메인 이벤트.
 */
public record RecruitMailRequestedEvent(
        String to,
        String subject,
        String content
) {
}
