package com.storage.central.api.repository;

import com.storage.central.api.entity.Credential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CredentialRepository extends JpaRepository<Credential, Long> {
    List<Credential> findByEmailId(String emailId);
}
