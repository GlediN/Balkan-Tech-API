package com.sda.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderWrite {
    private String name;
    private String contactNumber;
    private String address;
    private Double totalPrice;
    private String email;
    private String quantity; // This seems to be redundant and can be removed
    private List<OrderItemWrite> orderItems;
}
