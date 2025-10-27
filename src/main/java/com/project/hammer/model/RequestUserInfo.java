package com.project.hammer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestUserInfo{
    private String name;
    private String gmail;
    private String mobileNumber;
    private Integer roleElevation;
}
