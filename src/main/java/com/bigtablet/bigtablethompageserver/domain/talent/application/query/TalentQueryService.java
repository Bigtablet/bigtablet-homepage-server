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

    /**
     * ID로 인재 조회
     * @param idx Long 인재 ID
     * @return Talent 인재 도메인 객체
     */
    public Talent find(Long idx) {
        return talentJpaRepository
                .findById(idx)
                .map(Talent::of)
                .orElseThrow(() -> TalentNotFoundException.EXCEPTION);
    }

    /**
     * 이메일 중복 검증 (중복 시 예외 발생)
     * @param email String 검증할 이메일 주소
     * @return void
     */
    public void checkExistsByEmail(String email) {
        if (talentJpaRepository.existsByEmail(email)) {
            throw TalentAlreadyExistException.EXCEPTION;
        }
    }

    /**
     * 인재풀 목록 조회
     * @param isActive boolean 활성 여부
     * @param page int 페이지 번호
     * @param size int 페이지 크기
     * @return List<Talent> 인재 도메인 객체 목록
     */
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

    /**
     * 인재풀 키워드 검색
     * @param keyword String 검색 키워드
     * @param page int 페이지 번호
     * @param size int 페이지 크기
     * @return List<Talent> 인재 도메인 객체 목록
     */
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
