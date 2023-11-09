package com.sda.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@NamedQuery(name = "Category.getAllCategory", query = "select c from Category c where c.id in (select p.category from Product p where p.quantity>0)")
@Entity
@Table(name = "category")
@Data
@DynamicInsert
@DynamicUpdate
public class Category {
    @Id
    @Column(columnDefinition = "char(36)")
    private String id;
    @Column(unique = true)
    private String name;
    private String photo;
    private String parentId;

}
