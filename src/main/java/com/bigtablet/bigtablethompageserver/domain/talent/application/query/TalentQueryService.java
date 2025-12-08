package com.bigtablet.bigtablethompageserver.domain.talent.application.query;

import com.bigtablet.bigtablethompageserver.domain.talent.domain.model.Talent;
import com.bigtablet.bigtablethompageserver.domain.talent.domain.repository.query.TalentQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TalentQueryService {

    private final TalentQueryRepository talentQueryRepository;

    public List<Talent> findAllTalents(
            boolean isActive,
            int page,
            int size
    ) {
        return talentQueryRepository.findAllTalent(
                isActive,
                page,
                size
        );
    }

    public List<Talent> searchTalents(
            String keyword,
            int page,
            int size
    ) {
        return talentQueryRepository.searchTalent(
                keyword,
                page,
                size
        );
    }

}
