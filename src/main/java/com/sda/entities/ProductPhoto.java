package com.sda.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "product_photo")
@Data
@DynamicInsert
@DynamicUpdate
public class ProductPhoto {
    @Id
    @Column(columnDefinition = "char(36)")
    private String id;
    @ManyToOne
    private Product productId;
    private String productPhoto;

}
