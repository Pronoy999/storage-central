package com.storage.central.common.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Entity
@Table(name = "tbl_credential")
public class Credentials {
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @OneToOne
    @JoinColumn(name = "guid", nullable = false)
    private User user;
    @Column(name = "email_id", nullable = false)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "is_active")
    private boolean isActive;
    @CreationTimestamp
    private String created;
    @UpdateTimestamp
    private String updated;
}
