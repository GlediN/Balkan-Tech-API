package com.sda.controllers;

import com.sda.dto.LoginRequest;
import com.sda.dto.SignUpRequest;
import com.sda.services.EmailService;
import com.sda.services.UserService;
import com.sda.utils.HelpfulUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService userService;
    private final EmailService emailService;

    @PostMapping(path = "/signup")
    public ResponseEntity<String> signUp(@RequestBody SignUpRequest signUpRequest) {
        try {

            userService.signUp(signUpRequest);
            emailService.sendEmail(signUpRequest.getEmail(),"BalkanTech - Account Created Successfully","Dear " +signUpRequest.getName()+",\n\nThe account created successfully for BalkanTech. You may login using your email and password \n\nThank You,\nBalkanTech Team",null );
       return HelpfulUtils.getResponseEntity("Account created successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HelpfulUtils.getResponseEntity(HelpfulUtils.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        try {
            return userService.login(loginRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HelpfulUtils.getResponseEntity(HelpfulUtils.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(path = "/checkToken")
    public ResponseEntity<String> checkToken() {
        try {
            return userService.checkToken();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HelpfulUtils.getResponseEntity(HelpfulUtils.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
