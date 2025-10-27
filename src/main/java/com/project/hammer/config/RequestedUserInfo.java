package com.project.hammer.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RequestedUserInfo {
    private String userName;

    private String gmailAddress;

    private String role;
}
