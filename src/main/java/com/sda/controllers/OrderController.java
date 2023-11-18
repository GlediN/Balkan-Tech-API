package com.sda.controllers;

import com.sda.dto.OrderWrite;
import com.sda.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@CrossOrigin
public class OrderController {
    private final OrderService orderService;

    @PostMapping("save")
    public ResponseEntity<String> save(@RequestBody OrderWrite dto) {
        return orderService.saveOrder(dto);
    }

}
