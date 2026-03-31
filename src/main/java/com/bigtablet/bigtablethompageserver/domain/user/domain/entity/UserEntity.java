package com.bigtablet.bigtablethompageserver.domain.user.domain.entity;

import com.bigtablet.bigtablethompageserver.global.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@Table(name = "tb_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseEntity {

    // 이메일 (로그인 ID)
    @Id
    @Column(nullable = false, unique = true)
    private String email;

    // 암호화된 비밀번호
    @Column(nullable = false)
    private String password;

    // 사용자 이름
    @Column(nullable = false)
    private String name;

}
