package com.sda.services;

import com.sda.dto.LoginRequest;
import com.sda.dto.SignUpRequest;
import com.sda.entities.User;
import com.sda.enums.Roles;
import com.sda.jwt.CustomerUserDetailsService;
import com.sda.jwt.JwtFilter;
import com.sda.jwt.JwtUtil;
import com.sda.jwt.SecurityConfig;
import com.sda.repositories.UserDao;
import com.sda.utils.HelpfulUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;
    private final CustomerUserDetailsService customerUsersDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder bcryptPasswordEncoder;

    public ResponseEntity<String> signUp(SignUpRequest signUpRequest) {
        try {
            if (validateSignUpRequest(signUpRequest)) {
                User user = userDao.findByEmailId(signUpRequest.getEmail());
                if (Objects.isNull(user)) {
                    userDao.save(getUserFromSignUpRequest(signUpRequest));
                    return HelpfulUtils.getResponseEntity("Successfully Registered", HttpStatus.OK);
                } else {
                    return HelpfulUtils.getResponseEntity("Email already exists", HttpStatus.BAD_REQUEST);
                }
            } else {
                return HelpfulUtils.getResponseEntity(HelpfulUtils.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HelpfulUtils.getResponseEntity(HelpfulUtils.INVALID_DATA, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<String> login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
            if (authentication.isAuthenticated()) {
                return new ResponseEntity<>("{\"token\":\"" +
                        jwtUtil.generateToken(customerUsersDetailsService
                                .getUserDetail()
                                .getEmail(), customerUsersDetailsService.getUserDetail().getRole()) + "\"}", HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("{\"message\":\"Bad Credentials.\"}", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> checkToken() {
        try {
            return HelpfulUtils.getResponseEntity("true", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HelpfulUtils.getResponseEntity(HelpfulUtils.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateSignUpRequest(SignUpRequest signUpRequest) {
        return signUpRequest.getEmail() != null && signUpRequest.getPassword() != null;
    }

    private User getUserFromSignUpRequest(SignUpRequest signUpRequest) {
        User user = new User();
        user.setId(String.valueOf(UUID.randomUUID()));
        user.setRegisterDate(LocalDateTime.now());
        user.setCity(signUpRequest.getCity());
        user.setCountry(signUpRequest.getCountry());
        user.setName(signUpRequest.getName());
        user.setSurname(signUpRequest.getSurname());
        user.setContactNumber(signUpRequest.getContactNumber());
        user.setPassword(bcryptPasswordEncoder.encode(signUpRequest.getPassword()));
        user.setEmail(signUpRequest.getEmail());
        user.setAddress(signUpRequest.getAddress());
        user.setSubscription(signUpRequest.getSubscription());
        user.setRole(Roles.USER);
        return user;
    }


}