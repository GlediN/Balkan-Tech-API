package com.sda.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

public interface OrderRest {
    @PostMapping(path = "/checkout")
    public ResponseEntity<String> checkout(@RequestBody(required = true) Map<String, String> requestMap);

}
