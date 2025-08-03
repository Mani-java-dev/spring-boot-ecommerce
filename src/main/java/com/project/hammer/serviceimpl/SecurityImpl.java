package com.project.hammer.serviceimpl;

import com.project.hammer.config.JwtUtility;
import com.project.hammer.entity.Role;
import com.project.hammer.entity.Users;
import com.project.hammer.exceptions.BadRequestCustomException;
import com.project.hammer.model.LoginModel;
import com.project.hammer.model.RequestUserInfo;
import com.project.hammer.model.SignupModel;
import com.project.hammer.model.UsersDetails;
import com.project.hammer.repository.RoleRepo;
import com.project.hammer.repository.UserRepo;
import com.project.hammer.service.SecurityService;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.Collectors;

import static com.project.hammer.constants.Constant.ACTIVE;

@Service
@Slf4j
public class SecurityImpl implements SecurityService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder; // bean from security config

    @Override
    public void createNewUserProfiler(SignupModel signupModel) {
        if(Objects.isNull(signupModel.getGmail())){
            throw new BadRequestCustomException("Gmail shouldn't be empty");
        }
        Users checkIsExist=userRepo.findByGmail(signupModel.getGmail());

        if(Objects.isNull(checkIsExist)){
            Users createNewUser=new Users();
            createNewUser.setName(signupModel.getName());
            createNewUser.setGmail(signupModel.getGmail());
            createNewUser.setPassword(passwordEncoder.encode(signupModel.getPassword()));
            createNewUser.setConfirmPassword(passwordEncoder.encode(signupModel.getConfirmPassword()));
            createNewUser.setPhoneNumber(signupModel.getMobileNumber());
            createNewUser.setRole(roleRepo.findById(1L).get());
            createNewUser.setActiveStatus(ACTIVE);

            userRepo.save(createNewUser);
        }else{
            throw new BadRequestCustomException("User already exist !");
        }

    }

    @Override
    public Map<String, String> loginUser(LoginModel loginModel) {

        Users user = userRepo.findByGmail(loginModel.getGmail());

        if(Objects.isNull(user)){
            throw new BadRequestCustomException("User not found ");
        }

        if(user.getActiveStatus().equals("DEACTIVE")){
            throw new BadRequestCustomException("Your account is deactivated. please contact admin");
        }

//        if (!new BCryptPasswordEncoder().matches(userInputs.getPassword(), user.getPassword())){
//            HashMap<String,String> messages=new HashMap<String,String>();
//            messages.put("message","password does not match");
//            return messages;
//        }
// this is no need becase it is manual check

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginModel.getGmail(), loginModel.getPassword())
            ).isAuthenticated();
        }catch (AuthenticationException e){
            throw new BadRequestCustomException("invalid credentials");
        }

        Map<String, String> token = jwtUtility.generateJWt(loginModel);
        Authentication role= SecurityContextHolder.getContext().getAuthentication();
        return token;
    }

    @Override
    public List<UsersDetails> getAllUsers() {
        List<Users> usersList=userRepo.getAllUsers();
        if(Objects.isNull(usersList)||usersList.isEmpty()){
            log.info("Users details not found !");
            throw new BadRequestCustomException("Users details not found !");
        }
        return usersList.stream().map(n->{
            UsersDetails usersDetails=new UsersDetails();
            usersDetails.setName(n.getName());
            usersDetails.setRole(n.getRole().getRole());
            usersDetails.setGmail(n.getGmail());
            usersDetails.setIsActive(n.getActiveStatus());
            usersDetails.setPhoneNumber(n.getPhoneNumber());
            return usersDetails;
        }).toList();
    }

    @Override
    public String elevationUpdate(RequestUserInfo updateMode) {
        if(Objects.isNull(updateMode)||Objects.isNull(updateMode.getGmail())
                ||Objects.isNull(updateMode.getRoleElevation())){
            throw new BadRequestCustomException("user details shouldn't be empty");
        }
        Users user=userRepo.findByGmail(updateMode.getGmail());
        Optional<Role> role=roleRepo.findById(updateMode.getRoleElevation().longValue());
        if(role.isPresent()) {
            user.setRole(role.get());
        }else{
            throw new BadRequestCustomException("Role not found !");
        }
        userRepo.save(user);
        return "Role updated successfully";
    }

    @Override
    public String deativateUser(String userId) {
        if(Objects.isNull(userId)||userId.isEmpty()){
            throw new BadRequestCustomException("User id shouldn't be empty");
        }
        Users user=userRepo.findByGmail(userId);
         if(Objects.isNull(user)){
             throw new BadRequestCustomException("User not found");
         }
         if(user.getActiveStatus().equals("ACTIVE")){
             user.setActiveStatus("DEACTIVE");
         }else{
             user.setActiveStatus("ACTIVE");
         }
        userRepo.save(user);
        return "User Deactivated successfully";
    }
}
