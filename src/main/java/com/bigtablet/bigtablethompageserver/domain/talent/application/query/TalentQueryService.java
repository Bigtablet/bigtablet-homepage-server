package com.bigtablet.bigtablethompageserver.domain.talent.application.query;

import com.bigtablet.bigtablethompageserver.domain.talent.domain.model.Talent;
import com.bigtablet.bigtablethompageserver.domain.talent.domain.repository.jpa.TalentJpaRepository;
import com.bigtablet.bigtablethompageserver.domain.talent.domain.repository.query.TalentQueryRepository;
import com.bigtablet.bigtablethompageserver.domain.talent.exception.TalentAlreadyExistException;
import com.bigtablet.bigtablethompageserver.domain.talent.exception.TalentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TalentQueryService {

    private final TalentJpaRepository talentJpaRepository;
    private final TalentQueryRepository talentQueryRepository;

    public Talent find(Long idx) {
        return talentJpaRepository
                .findByIdx(idx)
                .map(Talent::of)
                .orElseThrow(() -> TalentNotFoundException.EXCEPTION);
    }

    public void checkExistsByEmail(String email) {
        if (talentJpaRepository.existsByEmail(email)) {
            throw TalentAlreadyExistException.EXCEPTION;
        }
    }

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
