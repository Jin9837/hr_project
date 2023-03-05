package com.example.hr_project.controller;

import com.example.hr_project.domain.entity.User;
import com.example.hr_project.domain.request.LoginRequest;
import com.example.hr_project.domain.response.LoginResponse;
import com.example.hr_project.exception.InvalidCredentialsException;
import com.example.hr_project.security.AuthUserDetail;
import com.example.hr_project.security.JwtProvider;
import com.example.hr_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private final UserService userService;

    private AuthenticationManager authenticationManager;

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    private JwtProvider jwtProvider;

    @Autowired
    public void setJwtProvider(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

//    @PostMapping("/login")
//    public ResponseEntity<User> login(@RequestBody LoginRequest loginRequest) throws InvalidCredentialsException {
//        User user = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
//        if (user != null) {
//            return ResponseEntity.ok(user);
//        } else {
//            return ResponseEntity.badRequest().build();
//        }
//    }




    @PostMapping("login")
    public LoginResponse login(@RequestBody LoginRequest request) throws InvalidCredentialsException{

        Authentication authentication;

        //Try to authenticate the user using the username and password
//        try{
//            authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
//            );
//        } catch (AuthenticationException e){
//            throw new BadCredentialsException("Provided credential is invalid.");
//        }

        authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        //Successfully authenticated user will be stored in the authUserDetail object
        AuthUserDetail authUserDetail = (AuthUserDetail) authentication.getPrincipal(); //getPrincipal() returns the user object

        //A token wil be created using the username/email/userId and permission
        String token = jwtProvider.createToken(authUserDetail);

        //Returns the token as a response to the frontend/postman
        return LoginResponse.builder()
                .message("Welcome " + authUserDetail.getUsername())
                .token(token)
                .build();

    }
}
