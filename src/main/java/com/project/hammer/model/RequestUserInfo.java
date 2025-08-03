package com.project.hammer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
