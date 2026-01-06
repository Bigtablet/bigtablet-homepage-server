package com.bigtablet.bigtablethompageserver.domain.recruit.application.query;

import com.bigtablet.bigtablethompageserver.domain.recruit.domain.enums.Status;
import com.bigtablet.bigtablethompageserver.domain.recruit.domain.model.Recruit;
import com.bigtablet.bigtablethompageserver.domain.recruit.domain.repository.query.RecruitQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecruitQueryService {

    private final RecruitQueryRepository recruitQueryRepository;

    public List<Recruit> findAllRecruits(
            int page,
            int size,
            Long jobId,
            Status status
    ) {
        return recruitQueryRepository
                .findRecruits(
                        page,
                        size,
                        jobId,
                        status
                )
                .stream()
                .map(Recruit::of)
                .toList();
    }

}
