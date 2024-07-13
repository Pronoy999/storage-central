package com.storage.central.common.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_credentials")
@Data
public class Credential {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_guid", nullable = false)
    private String userGuid;
    @Column(name = "email_id", nullable = false)
    private String emailId;
    @Column(name = "password")
    private String password;
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
    @CreationTimestamp
    private LocalDateTime created;
    @UpdateTimestamp
    private LocalDateTime updated;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_guid", referencedColumnName = "guid", insertable = false)
    private User user;
}
