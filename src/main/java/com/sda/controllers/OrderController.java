package com.sda.controllers;

import com.sda.dto.OrderWrite;
import com.sda.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    protected final OrderService orderService;

    @PostMapping("save")
    public ResponseEntity<String> save(@RequestBody OrderWrite dto) {
        return orderService.saveOrder(dto);
    }

}
