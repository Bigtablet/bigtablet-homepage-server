package com.bigtablet.bigtablethompageserver.domain.recruit.domain.entity;

import com.bigtablet.bigtablethompageserver.domain.recruit.domain.enums.EducationLevel;
import com.bigtablet.bigtablethompageserver.domain.recruit.domain.enums.Military;
import com.bigtablet.bigtablethompageserver.global.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@Table(name = "tb_recruit")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecruitEntity extends BaseEntity {

    @Id
    @Column(nullable = false, unique = true, updatable = false)
    private Long idx;

    @Column(nullable = false)
    private Long jobId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String addressDetail;

    @Column(nullable = false)
    private String portfolio;

    private String coverLetter;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EducationLevel educationLevel;

    private String schoolName;

    private String admissionYear;

    private String graduationYear;

    private String department;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Military military;

    private String attachment1;

    private String attachment2;

    private String attachment3;

}
