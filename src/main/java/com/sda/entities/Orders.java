package com.sda.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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
    private Date orderDate;
    @ManyToOne
    private User user;
}
