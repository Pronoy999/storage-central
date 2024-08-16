package com.storage.central.api.repository;

import com.storage.central.api.entity.FileMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileMasterRepository extends JpaRepository<FileMaster, String> {
}
