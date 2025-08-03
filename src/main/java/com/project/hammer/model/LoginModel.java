package com.project.hammer.model;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginModel {
    @NonNull
    @NotBlank
    private String gmail;
    @NonNull
    @NotBlank
    private String password;

}