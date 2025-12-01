package com.bigtablet.bigtablethompageserver.domain.job.domain.repository.query.impl;

import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Department;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Education;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.RecruitType;
import com.bigtablet.bigtablethompageserver.domain.job.domain.model.Job;
import com.bigtablet.bigtablethompageserver.domain.job.domain.repository.query.JobQueryRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.bigtablet.bigtablethompageserver.domain.job.domain.entity.QJobEntity.jobEntity;

@Repository
@RequiredArgsConstructor
public class JobQueryRepositoryImpl implements JobQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Job> findJobList(
            int page,
            int size,
            String title,
            Department department,
            Education education,
            RecruitType recruitType
    ) {
        BooleanBuilder condition = buildJobSearchCondition(
                true,
                title,
                department,
                education,
                recruitType
        );
        return jpaQueryFactory
                .selectFrom(jobEntity)
                .where(condition)
                .offset((long) (page - 1) * size)
                .limit(size)
                .orderBy(jobEntity.createdAt.desc())
                .fetch()
                .stream()
                .map(Job::of)
                .toList();
    }

    @Override
    public List<Job> findDeactivateJobList(
            int page,
            int size,
            String title,
            Department department,
            Education education,
            RecruitType recruitType
    ) {
        BooleanBuilder condition = buildJobSearchCondition(
                false,
                title,
                department,
                education,
                recruitType
        );
        return jpaQueryFactory
                .selectFrom(jobEntity)
                .where(condition)
                .offset((long) (page - 1) * size)
                .limit(size)
                .orderBy(jobEntity.createdAt.desc())
                .fetch()
                .stream()
                .map(Job::of)
                .toList();
    }

    private BooleanBuilder buildJobSearchCondition(
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
