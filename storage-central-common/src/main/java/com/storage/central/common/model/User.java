package com.storage.central.common.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class User {
    @Id
    private String guid;
    private String firstName;
    private String lastName;
    private String created;
    private String updated;
}
