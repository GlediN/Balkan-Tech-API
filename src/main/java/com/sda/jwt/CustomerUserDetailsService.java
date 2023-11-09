package com.sda.jwt;

import com.sda.dao.UserDao;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CustomerUserDetailsService implements UserDetailsService {

    final UserDao userDao;
    private com.sda.entities.User userDetail;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        userDetail = userDao.findByEmailId(username);
        if (!Objects.isNull(userDetail))
            return new org.springframework.security.core.userdetails.User(userDetail.getEmail(), userDetail.getPassword(), new ArrayList<>());
        else
            throw new UsernameNotFoundException("User not found");
    }

    public com.sda.entities.User getUserDetail(){
        return userDetail;
    }

}
