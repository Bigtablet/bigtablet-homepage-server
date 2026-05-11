package com.bigtablet.bigtablethompageserver.domain.admin.domain.entity;

import com.bigtablet.bigtablethompageserver.domain.admin.domain.enums.AdminRole;
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
@Table(name = "tb_admin")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminEntity extends BaseEntity {

    // 관리자 고유 ID (UUID)
    @Id
    @Column(nullable = false, unique = true, updatable = false)
    private String id;
    // 관리자 이메일 (@bigtablet.com 도메인 강제)
    @Column(nullable = false, unique = true)
    private String email;
    // 관리자 권한 (현재는 ADMIN 단일)
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AdminRole role;

}
