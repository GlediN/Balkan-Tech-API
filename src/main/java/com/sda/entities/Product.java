package com.sda.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;


@Entity
@Table(name = "product")
@Data
@DynamicInsert
@DynamicUpdate
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String name;
    @Column(columnDefinition = "text")
    private String description;
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;
    private double price;
    private double discount;
    private Integer soldQty;
    private String imageUrl;

   /* @OneToMany(mappedBy = "productId")
    private List<ProductPhoto> productPhotos;*/

}
