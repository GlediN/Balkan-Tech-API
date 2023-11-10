package com.sda.serviceImpl;

import com.sda.dao.UserDao;
import com.sda.entities.User;
import com.sda.enums.Roles;
import com.sda.jwt.CustomerUserDetailsService;
import com.sda.jwt.JwtFilter;
import com.sda.jwt.JwtUtil;
import com.sda.service.UserService;
import com.sda.utils.HelpfulUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    final UserDao userDao;

    final CustomerUserDetailsService customerUsersDetailsService;

    final AuthenticationManager authenticationManager;

    final JwtUtil jwtUtil;

    final JwtFilter jwtFilter;


    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        try {
            if (validateSignUpMap(requestMap)) {
                User user = userDao.findByEmailId(requestMap.get("email"));
                if (Objects.isNull(user)) {
                    userDao.save(getUserFromMap(requestMap));
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

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password"))
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


    private boolean validateSignUpMap(Map<String, String> requestMap) {
        if (requestMap.containsKey("contactNumber")
                && requestMap.containsKey("email") && requestMap.containsKey("password")) {
            return true;
        } else {
            return false;
        }

    }

    private User getUserFromMap(Map<String, String> requestMap) {
        User user = new User();
        user.setId(String.valueOf(UUID.randomUUID()));
        user.setRegisterDate(LocalDateTime.now());
        user.setCity(requestMap.get("city"));
        user.setCountry(requestMap.get("country"));
        user.setName(requestMap.get("name"));
        user.setSurname(requestMap.get("surname"));
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setPassword(requestMap.get("password"));
        user.setEmail(requestMap.get("email"));
        user.setAddress(requestMap.get("address"));
        user.setSubscription(requestMap.get("subscription"));
        user.setRole(Roles.USER);
        return user;
    }




    @Override
    public ResponseEntity<String> checkToken() {
        return HelpfulUtils.getResponseEntity("true", HttpStatus.OK);
    }




}
