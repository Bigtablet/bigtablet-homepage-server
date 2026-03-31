package com.bigtablet.bigtablethompageserver.domain.talent.domain.entity;

import com.bigtablet.bigtablethompageserver.global.common.entity.BaseEntity;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@SuperBuilder
@Table(name = "tb_talent")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TalentEntity extends BaseEntity {

    // 고유 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    // 이메일
    @Column(nullable = false, unique = true)
    private String email;

    // 이름
    @Column(nullable = false)
    private String name;

    // 부서/직군
    @Column(nullable = false)
    private String department;

    // 포트폴리오 URL
    @Column(nullable = false)
    private String portfolioUrl;

    // 기타 URL 목록
    @ElementCollection
    @CollectionTable(
            name = "tb_url",
            joinColumns = @JoinColumn(name = "talent_id")
    )
    private List<String> etcUrl;

    // 활성 상태
    @Column(nullable = false)
    private boolean isActive;

    /**
     * 인재를 활성화한다
     */
    public void activate() {
        this.isActive = true;
    }

    /**
     * 인재를 비활성화한다
     */
    public void deactivate() {
        this.isActive = false;
    }

}
