package com.sda.service;

import com.sda.dto.OrderWrite;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface OrderService {
    ResponseEntity<String> getOrder(OrderWrite orderWrite);

    ResponseEntity<String> saveOrder(OrderWrite dto);
}
