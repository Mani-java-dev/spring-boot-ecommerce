package com.project.hammer.service;

import com.project.hammer.model.LoginModel;
import com.project.hammer.model.RequestUserInfo;
import com.project.hammer.model.SignupModel;
import com.project.hammer.model.UsersDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface SecurityService {

    void createNewUserProfiler(SignupModel signupModel);

    Map<String, String> loginUser(LoginModel loginModel);

    List<UsersDetails> getAllUsers();

    String elevationUpdate(RequestUserInfo updateMode);


    String deativateUser(String userId);
}
