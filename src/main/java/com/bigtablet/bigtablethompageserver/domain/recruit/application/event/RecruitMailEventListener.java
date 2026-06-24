package com.bigtablet.bigtablethompageserver.domain.recruit.application.event;

import com.bigtablet.bigtablethompageserver.global.infra.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class RecruitMailEventListener {

    private final EmailService emailService;

    /**
     * 상태 변경 트랜잭션이 커밋된 뒤에만 채용 메일을 발송한다(롤백 시 오발송 방지).
     * EmailService.sendRecruit 자체가 @Async 라 발송은 별도 스레드로 위임된다.
     * @param event 발송할 메일 정보
     */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(final RecruitMailRequestedEvent event) {
        emailService.sendRecruit(event.to(), event.subject(), event.content());
    }

}
