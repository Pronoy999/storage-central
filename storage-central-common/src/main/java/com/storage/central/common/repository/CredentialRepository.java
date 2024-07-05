package com.storage.central.common.repository;

import com.storage.central.common.model.Credentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CredentialRepository extends JpaRepository<Credentials, Integer> {
}
