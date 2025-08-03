package com.project.hammer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersDetails {
    private String name;
    private String gmail;
    private String phoneNumber;
    private String isActive;
    private String role;
}
