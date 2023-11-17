package com.sda.service;

import com.sda.dto.OrderWrite;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface OrderService {
    ResponseEntity<String> getOrder(Map<String,String> requestMap);
    ResponseEntity<String> saveOrder(OrderWrite dto);
}
