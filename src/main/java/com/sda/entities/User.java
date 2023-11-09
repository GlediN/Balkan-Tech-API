package com.sda.entities;

import com.sda.enums.Roles;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@NamedQuery(name = "User.findByEmailId", query = "select u from User u where u.email=:email")
@Entity
@Table(name = "user")
@Data
@DynamicInsert
@DynamicUpdate
public class User {
    @Id
    private String id;
    @Column(unique = true)
    private String email;
    private String name;
    private String surname;
    private String password;
    private String contactNumber;
    private String city;
    private String country;
    private String address;
    @Enumerated(EnumType.STRING)
    private Roles role;
    private LocalDateTime registerDate;
    private String subscription;

    public void setPassword(String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

}

