package com.bigtablet.bigtablethompageserver.domain.job.application.scheduler;

import com.bigtablet.bigtablethompageserver.domain.job.domain.repository.jpa.JobJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobScheduler {

	private final JobJpaRepository jobJpaRepository;

	/**
	 * 마감일이 지난 채용 공고를 자동 비활성화하는 스케줄러 (매일 자정 실행).
	 * entity hydration 없이 bulk UPDATE 단일 쿼리로 처리한다.
	 */
	@Transactional
	@Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
	public void deactivateEndedJobs() {
		LocalDate yesterday = LocalDate.now(ZoneId.of("Asia/Seoul")).minusDays(1);
		int affected = jobJpaRepository.deactivateAllByEndDate(yesterday);
		if (affected > 0) {
			log.info("[JobScheduler] deactivateEndedJobs - count={}, time={}", affected, LocalDateTime.now());
		}
	}

}
