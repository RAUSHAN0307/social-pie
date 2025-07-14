package com.rk.social_media.controller;

import com.rk.social_media.config.JwtProvider;
import com.rk.social_media.entity.User;
import com.rk.social_media.repo.UserRepo;
import com.rk.social_media.request.LoginRequest;
import com.rk.social_media.request.RegisterRequest;
import com.rk.social_media.response.AuthResponse;
import com.rk.social_media.service.UploadToCloudService;
import com.rk.social_media.service.serviceImpl.CustomUserDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UploadToCloudService uploadToCloudService;

    @Operation(summary = "Register a new user")
    @PostMapping(value = "/register" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public AuthResponse CreateUser(@ModelAttribute  RegisterRequest request,
                                   @RequestPart(value = "file" , required = false) MultipartFile file) throws Exception {

        User isExist = userRepo.findByEmail(request.getEmail());
        if(isExist != null) throw new Exception("email is already registered");

        String profileUrl = null;
        if (file != null && !file.isEmpty()) {
            profileUrl = uploadToCloudService.uploadImage(file);
        }

        // setting user
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setGender(request.getGender());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setProfilePic(profileUrl);
        userRepo.save(user);

        // authentication/tokenGeneration
        //********
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        String token = JwtProvider.generateToken(authentication);
        AuthResponse response = new AuthResponse(token , "registered successfully");
        return response;
    }

    // sign in

    @Operation(summary = "Login with email and password")
    @PostMapping("/signin")
    public AuthResponse signin(@RequestBody LoginRequest loginRequest) throws Exception {
        // ***************
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        String token = JwtProvider.generateToken(authentication);
        AuthResponse response = new AuthResponse(token , "login successfully");
        return response;
    }

}

// if you are implementing frontend then
// @RequestPart("user")RegisterRequest request this will take data as json+file
// if postman :- then use modelAttribute + requestPart
// here for swagger i am using the requestParam + modelAttribute