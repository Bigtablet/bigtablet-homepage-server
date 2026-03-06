package com.bigtablet.bigtablethompageserver.domain.job.application.scheduler;

import com.bigtablet.bigtablethompageserver.domain.job.domain.entity.JobEntity;
import com.bigtablet.bigtablethompageserver.domain.job.domain.repository.jpa.JobJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobScheduler {

	private final JobJpaRepository jobJpaRepository;

	@Transactional
	@Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
	public void deactivateEndedJobs() {
		LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));
		List<JobEntity> jobsEnded = jobJpaRepository.findAll().stream()
				.filter(j -> j.getEndDate() != null
						&& j.getEndDate().plusDays(1).isEqual(today))
				.toList();
		if (!jobsEnded.isEmpty()) {
			jobsEnded.forEach(j -> j.deactivate());
			jobJpaRepository.saveAll(jobsEnded);
			log.info("[JobScheduler] deactivateEndedJobs - count={}, time={}", jobsEnded.size(), LocalDateTime.now());
		}
		log.info("[JobScheduler] deactivateEndedJobs - completed, time={}", LocalDateTime.now());
	}

}
