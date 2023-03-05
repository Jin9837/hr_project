package com.example.hr_project.domain.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {

    private String username;
    private String password;
}
