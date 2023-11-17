package com.sda.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;



@Entity
@Table(name = "category")
@Data
@DynamicInsert
@DynamicUpdate
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String name;
    private String photo;
    private String parentId;

}