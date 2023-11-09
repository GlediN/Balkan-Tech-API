package com.sda.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Date;

@Entity
@Table(name = "order_item")
@Data
@DynamicInsert
@DynamicUpdate
public class OrderItem {
    @Id
    @Column(columnDefinition = "char(36)")
    private String id;
    private String quantity;
    private String price;
    @ManyToOne
    private Product productId;
    @ManyToOne
    private Orders orderId;
}
