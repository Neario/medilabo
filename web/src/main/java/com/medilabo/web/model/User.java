package com.medilabo.web.model;

import com.medilabo.web.model.enumerations.Role;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String identifier;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    public String toString() {
        return identifier;
    }
}
