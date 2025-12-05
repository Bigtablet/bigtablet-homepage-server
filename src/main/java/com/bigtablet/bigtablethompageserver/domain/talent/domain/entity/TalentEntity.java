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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private String portfolioUrl;

    @ElementCollection
    @CollectionTable(
            name = "tb_url",
            joinColumns = @JoinColumn(name = "talent_id")
    )
    private List<String> etcUrl;

}
