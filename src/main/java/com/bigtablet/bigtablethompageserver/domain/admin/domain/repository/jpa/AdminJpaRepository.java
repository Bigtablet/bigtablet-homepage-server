package com.bigtablet.bigtablethompageserver.domain.admin.domain.repository.jpa;

import com.bigtablet.bigtablethompageserver.domain.admin.domain.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminJpaRepository extends JpaRepository<AdminEntity, String> {

    Optional<AdminEntity> findByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String email);

}
