package com.sda.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderWrite {
    protected String name;
    protected String surname;
    protected String contactNumber;
    protected String address;
    protected String totalPrice;
    protected String email;
    protected String quantity;
    protected List<OrderItemWrite> orderItems;
}
