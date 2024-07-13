package com.storage.central.api.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "tbl_credentials", schema = "storage_central")
public class Credential {
    @Id
    private String id;
    @Column(name = "user_guid", nullable = false)
    private String userGuid;
    @Column(name = "email_id", nullable = false)
    private String emailId;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;
    @CreationTimestamp
    @Column(name = "created")
    private LocalDateTime created;
    @UpdateTimestamp
    @Column(name = "updated")
    private LocalDateTime updated;
    @ManyToOne
    @JoinColumn(name = "user_guid", referencedColumnName = "guid", insertable = false, updatable = false)
    private User user;
}
