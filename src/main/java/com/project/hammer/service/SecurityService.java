package com.project.hammer.service;

import com.project.hammer.model.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface SecurityService {

    void createNewUserProfiler(SignupModel signupModel);

    LoginResponse loginUser(LoginModel loginModel);

    List<UsersDetails> getAllUsers();

    String elevationUpdate(RequestUserInfo updateMode);


    String deativateUser(String userId);
}
