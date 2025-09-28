package com.project.hammer.controller;

import com.project.hammer.constants.APIResponse;
import com.project.hammer.model.LoginModel;
import com.project.hammer.model.RequestUserInfo;
import com.project.hammer.model.SignupModel;
import com.project.hammer.service.SecurityService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.project.hammer.constants.Constant.*;

@RestController
@RequestMapping("/hammer/v1/api")
@Slf4j
public class SecurityController {

       @Autowired
       private SecurityService securityService;

      @PostMapping("/sign")
      public ResponseEntity<APIResponse> addNewUser(@RequestBody SignupModel signupModel,HttpServletRequest request ){
          log.info("{}{}",TRACKER, request.getRemoteAddr());
          securityService.createNewUserProfiler(signupModel);
          return ResponseEntity.status(HttpStatus.OK).body(new APIResponse(SUCCESS,SIGNUP_SUCCESSFULL,null));
      }

      @PostMapping("/login")
      public ResponseEntity<APIResponse> LoginUser(@RequestBody LoginModel loginModel, HttpServletRequest httpServletRequest){
          log.info("{}{}",TRACKER, httpServletRequest.getRemoteAddr());
          return ResponseEntity.status(HttpStatus.OK).body(new APIResponse(SUCCESS,LOGIN_SUCCESSFULL,securityService.loginUser(loginModel)));
      }

      @GetMapping("/all/users")
      public ResponseEntity<APIResponse> fetchAllUsers(HttpServletRequest httpServletRequest){
          log.info("{}{}",TRACKER, httpServletRequest.getRemoteAddr());
          return ResponseEntity.status(HttpStatus.OK).body(new APIResponse(SUCCESS,SUCCESSFULLY_FETCH_ALL,securityService.getAllUsers()));
      }


      @PutMapping("/user/elevate")
      public ResponseEntity<APIResponse> elevateUser(@RequestBody RequestUserInfo updateMode, HttpServletRequest httpServletRequest){
          log.info("{}{}",TRACKER, httpServletRequest.getRemoteAddr());
          return ResponseEntity.status(HttpStatus.OK).body(new APIResponse(SUCCESS,LOGIN_SUCCESSFULL,securityService.elevationUpdate(updateMode)));
      }

    @PatchMapping("/user/deactivate")
    public ResponseEntity<APIResponse> deactivateUser(@RequestParam String userId, HttpServletRequest httpServletRequest){
        log.info("{}{}",TRACKER, httpServletRequest.getRemoteAddr());
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse(SUCCESS,LOGIN_SUCCESSFULL,securityService.deativateUser(userId)));
    }



}
