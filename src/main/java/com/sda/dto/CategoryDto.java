package com.sda.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {
    private Integer id;
    private String name;
    private String photo;
    private String parentId;


}
