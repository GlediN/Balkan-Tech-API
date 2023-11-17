package com.sda.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemWrite {
    protected String productId;
    protected String quantity;
}
