package com.project.hammer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "users")
@Data
public class Users {

    @Setter
    @Getter
    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @NotBlank
    @Size(min=3, max=20)
    private String name;

    @NotBlank
    @NotNull
    @Size
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*]).{8,}$",
            message = "Password must be at least 8 characters and one special char and one digit")
    private String password;


    @Transient// don't save this on database
    @JsonIgnore// don't send response to client. only for write value (particular field)
    private String confirmPassword;


    @NotBlank
    @NotNull
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    private String phoneNumber;

    @Email
    @NotBlank
    private String gmail;

    private String ActiveStatus;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    //mapped by user -> is field from orders entity (mapped varible name) name name of the entity
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Orders> orders;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "card")
    private Cart cart;

    @AssertTrue(message = "Passwords do not match") //custom query
    private boolean isPasswordMatching() {

        if(password.length()>20)
        {
            return true;
        }
        return password.equals(confirmPassword);
    }



}
