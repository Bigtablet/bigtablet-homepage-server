package com.bigtablet.bigtablethompageserver.domain.talent.domain.repository.query;

import com.bigtablet.bigtablethompageserver.domain.talent.domain.model.Talent;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.bigtablet.bigtablethompageserver.domain.talent.domain.entity.QTalentEntity.talentEntity;

@Repository
@RequiredArgsConstructor
public class TalentQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<Talent> findAllTalent(boolean isActive, int page, int size) {
        return jpaQueryFactory
                .selectFrom(talentEntity)
                .where(talentEntity.isActive.eq(isActive))
                .offset((long) (page - 1) * size)
                .limit(size)
                .orderBy(talentEntity.createdAt.desc())
                .fetch()
                .stream()
                .map(Talent::of)
                .toList();
    }

    public List<Talent> searchTalent(
            String keyword,
            int page,
            int size
    ) {
        return jpaQueryFactory
                .selectFrom(talentEntity)
                .where(
                        talentEntity.isActive.eq(true)
                                .and(
                                        talentEntity.name.containsIgnoreCase(keyword)
                                                .or(talentEntity.email.containsIgnoreCase(keyword))
                                                .or(talentEntity.department.containsIgnoreCase(keyword))
                                )
                )
                .offset((long) (page - 1) * size)
                .limit(size)
                .orderBy(talentEntity.createdAt.desc())
                .fetch()
                .stream()
                .map(Talent::of)
                .toList();
    }

}
