package com.sda.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {
    private Integer id;
    private String name;
    private String description;
    private Integer quantity;
    private Integer categoryId;
    private double price;
    private double discount;
    private Integer soldQty;
    private String imageUrl;
}
