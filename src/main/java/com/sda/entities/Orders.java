package com.sda.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "orders")
@Data
@DynamicInsert
@DynamicUpdate
public class Orders {
    @Id
    @Column(columnDefinition = "char(36)")
    private String id;
    private String name;
    private String email;
    private String contactNumber;
    private String address;
    private double totalPrice;
    private LocalDateTime orderDate;
    @ManyToOne
    private User user;
}
