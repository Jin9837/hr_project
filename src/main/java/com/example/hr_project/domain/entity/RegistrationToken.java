package com.example.hr_project.domain.entity;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "registration_token")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class RegistrationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "token")
    private String token;

    @Column(name = "email")
    private String email;

    @Column(name = "expiration_date")
    private Timestamp expirationDate;

    @Column(name = "created_by")
    private String createdBy;

}

