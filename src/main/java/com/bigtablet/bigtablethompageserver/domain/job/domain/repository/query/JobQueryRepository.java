package com.bigtablet.bigtablethompageserver.domain.job.domain.repository.query;

import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Department;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Education;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.RecruitType;
import com.bigtablet.bigtablethompageserver.domain.job.domain.model.Job;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.bigtablet.bigtablethompageserver.domain.job.domain.entity.QJobEntity.jobEntity;

@Repository
@RequiredArgsConstructor
public class JobQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<Job> findJobList(
            int page,
            int size,
            String title,
            Department department,
            Education education,
            RecruitType recruitType
    ) {
        BooleanBuilder builder = buildJob(
                true,
                title,
                department,
                education,
                recruitType
        );
        return jpaQueryFactory
                .selectFrom(jobEntity)
                .where(builder)
                .offset((long) (page - 1) * size)
                .limit(size)
                .orderBy(jobEntity.createdAt.desc())
                .fetch()
                .stream()
                .map(Job::of)
                .toList();
    }

    public List<Job> findDeactivateJobList(
            int page,
            int size,
            String title,
            Department department,
            Education education,
            RecruitType recruitType
    ) {
        BooleanBuilder builder = buildJob(
                false,
                title,
                department,
                education,
                recruitType
        );
        return jpaQueryFactory
                .selectFrom(jobEntity)
                .where(builder)
                .offset((long) (page - 1) * size)
                .limit(size)
                .orderBy(jobEntity.createdAt.desc())
                .fetch()
                .stream()
                .map(Job::of)
                .toList();
    }

    private BooleanBuilder buildJob(
            boolean isActive,
            String title,
            Department department,
            Education education,
            RecruitType recruitType
    ) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(jobEntity.isActive.eq(isActive));
        if (title != null && !title.isBlank()) {
            builder.and(jobEntity.title.containsIgnoreCase(title));
        }
        if (department != null) {
            builder.and(jobEntity.department.eq(department));
        }
        if (education != null) {
            builder.and(jobEntity.education.eq(education));
        }
        if (recruitType != null) {
            builder.and(jobEntity.recruitType.eq(recruitType));
        }
        return builder;
    }

}
