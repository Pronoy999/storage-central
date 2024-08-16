package com.storage.central.api.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_FileMaster")
@Data
public class FileMaster {
    @Id
    @Column(name = "file_guid", nullable = false)
    private String fileId;
    @Column(name = "file_name", nullable = false)
    private String fileName;
    @Column(name = "file_extension")
    private String fileExtension;
    @Column(name = "sha_value")
    private String shaValue;
    @Column(name = "user_guid", nullable = false)
    private String userGuid;
    @CreationTimestamp
    private LocalDateTime created;
    @UpdateTimestamp
    private LocalDateTime updated;
    @OneToMany
    @JoinColumn(name = "user_guid", referencedColumnName = "guid", insertable = false, updatable = false)
    private User user;
}
