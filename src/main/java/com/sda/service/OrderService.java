package com.sda.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface OrderService {
    ResponseEntity<String> getOrder(Map<String,String> requestMap);
    ResponseEntity<String> saveOrder(Map<String,String> requestMap);
}
