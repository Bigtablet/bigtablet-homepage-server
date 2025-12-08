package com.bigtablet.bigtablethompageserver.domain.talent.domain.repository.query;

import com.bigtablet.bigtablethompageserver.domain.talent.domain.model.Talent;

import java.util.List;

public interface TalentQueryRepository {

    List<Talent> findAllTalent(
            boolean isActive,
            int page,
            int size
    );

    List<Talent> searchTalent(
            String keyword,
            int page,
            int size
    );

}
