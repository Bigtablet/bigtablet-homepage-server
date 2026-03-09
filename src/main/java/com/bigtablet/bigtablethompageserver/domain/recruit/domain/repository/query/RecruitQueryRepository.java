package com.bigtablet.bigtablethompageserver.domain.recruit.domain.repository.query;

import com.bigtablet.bigtablethompageserver.domain.recruit.domain.entity.RecruitEntity;
import com.bigtablet.bigtablethompageserver.domain.recruit.domain.enums.Status;
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

    public List<RecruitEntity> findRecruits(
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
                .toList();
    }

    private BooleanBuilder builder(
            Long jobId,
            Status status
    ) {
        BooleanBuilder builder = new BooleanBuilder();
        if (jobId != null) {
            builder.and(recruitEntity.jobId.eq(jobId));
        }
        if (status != null) {
            builder.and(recruitEntity.status.eq(status));
        }
        return builder;
    }

}
