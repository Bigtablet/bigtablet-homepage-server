package com.bigtablet.bigtablethompageserver.domain.recruit.domain.repository.query;

import com.bigtablet.bigtablethompageserver.domain.recruit.domain.enums.Status;
import com.bigtablet.bigtablethompageserver.domain.recruit.domain.model.Recruit;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.bigtablet.bigtablethompageserver.domain.recruit.domain.entity.QRecruitEntity.recruitEntity;

@Repository
@RequiredArgsConstructor
public class RecruitQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 페이지네이션 + jobId/status 필터로 지원서 목록 조회
     * @param page int 페이지 번호 (1부터 시작)
     * @param size int 페이지 크기
     * @param jobId Long 채용 공고 ID (필수)
     * @param status Status 지원서 상태 (nullable)
     * @return List<Recruit> 지원서 도메인 객체 목록
     */
    public List<Recruit> findRecruits(
            int page,
            int size,
            Long jobId,
            Status status
    ) {
        BooleanBuilder builder = builder(jobId, status);
        return jpaQueryFactory
                .selectFrom(recruitEntity)
                .where(builder)
                .offset((long) (page - 1) * size)
                .limit(size)
                .orderBy(recruitEntity.createdAt.desc())
                .fetch()
                .stream()
                .map(Recruit::of)
                .toList();
    }

    /**
     * jobId/status 조건 BooleanBuilder 생성
     * @param jobId Long 채용 공고 ID (필수, null 시 IllegalArgumentException)
     * @param status Status 지원서 상태 (nullable)
     * @return BooleanBuilder QueryDSL 조건 빌더
     */
    private BooleanBuilder builder(
            Long jobId,
            Status status
    ) {
        if (jobId == null) {
            throw new IllegalArgumentException("jobId must not be null");
        }
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(recruitEntity.jobId.eq(jobId));
        if (status != null) {
            builder.and(recruitEntity.status.eq(status));
        }
        return builder;
    }

}
