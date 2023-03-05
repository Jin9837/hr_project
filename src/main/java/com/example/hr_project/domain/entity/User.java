package com.example.hr_project.domain.entity;

import lombok.*;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "User")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "create_date", nullable = false)
    private Timestamp createDate;

    @Column(name = "last_modification_date", nullable = false)
    private Timestamp lastModificationDate;

    @Column(name = "active_flag", nullable = false)
    private boolean activeFlag;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserRole> userRoles;

    public String getUserRole() {
        if (userRoles == null) {
            return null;
        }
        for (UserRole userRole : userRoles) {
            if (userRole.getUser().getId() == this.id) {
                Role role = userRole.getRole();
                if (role != null) {
                    return role.getRoleName();
                } else {
                    return null;
                }
            }
        }
        return null;
    }
}
