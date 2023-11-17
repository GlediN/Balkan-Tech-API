package com.sda.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
@DynamicInsert
@DynamicUpdate
public class Order {
    @Id
    @Column(columnDefinition = "char(36)")
    private String id;
    private String name;
    private String email;
    private String contactNumber;
    private String address;
    private Double totalPrice;
    private LocalDateTime orderDate;
    @ManyToOne
    private User user;
//    @Column(name = "product_id")
//    private Long productId;
}
