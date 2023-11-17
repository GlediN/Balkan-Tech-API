package com.sda.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "order_item")
@Data
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String quantity;
    private String price;
    @ManyToOne
    private Product productId;
    @ManyToOne
    private Order orderId;

    public void setProduct(Product product) {
    }
}
