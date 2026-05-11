package com.bigtablet.bigtablethompageserver.domain.admin.domain.repository.jpa;

import com.bigtablet.bigtablethompageserver.domain.admin.domain.entity.WebAuthnCredentialEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WebAuthnJpaRepository extends JpaRepository<WebAuthnCredentialEntity, Long> {

    List<WebAuthnCredentialEntity> findAllByAdminId(String adminId);

    Optional<WebAuthnCredentialEntity> findByCredentialId(String credentialId);

    boolean existsByAdminId(String adminId);

}
