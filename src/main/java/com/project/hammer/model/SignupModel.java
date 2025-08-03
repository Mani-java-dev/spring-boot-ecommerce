package com.project.hammer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupModel {
    private String name;
    private String gmail;
    private String password;
    private String mobileNumber;
    private String confirmPassword;
}
