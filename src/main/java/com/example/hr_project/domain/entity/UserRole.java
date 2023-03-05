package com.example.hr_project.domain.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "UserRole")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private Role role;

    @Column(name = "active_flag")
    private boolean activeFlag;

    @Column(name = "create_date")
    private Timestamp createDate;

    @Column(name = "last_modification_date")
    private Timestamp lastModificationDate;

}


