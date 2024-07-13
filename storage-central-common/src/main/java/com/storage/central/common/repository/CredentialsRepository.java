package com.storage.central.common.repository;

import com.storage.central.common.model.entity.Credential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CredentialsRepository extends JpaRepository<Credential, Long> {
}
