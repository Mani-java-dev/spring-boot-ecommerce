package com.project.hammer.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class LoginResponse {
    private String token;
    private String userName;
    private String role;
}
