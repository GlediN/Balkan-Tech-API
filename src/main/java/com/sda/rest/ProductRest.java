package com.sda.rest;

import com.sda.entities.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RequestMapping(path ="/product")
public interface ProductRest {

    @PostMapping(path="/add")
    ResponseEntity<String> addNewProduct(@RequestBody Map<String, String> requestMap);

    @GetMapping(path="/get")
    ResponseEntity<List<Product>> getAllProduct();

    @PostMapping(path = "/update")
    ResponseEntity<String> updateProduct(@RequestBody Map<String, String> requestMap);

    @PostMapping(path="/delete/{id}")
    ResponseEntity<String> deleteProduct(@PathVariable Integer id);

    @PostMapping(path = "/updateQuantity")
    ResponseEntity<String>updateQuantity(@RequestBody Map<String, String> requestMap);

    @GetMapping(path = "/getByCategory/{id}")
    ResponseEntity<List<Product>> getByCategory(@PathVariable Integer id);

    @GetMapping(path="/getById/{id}")
    ResponseEntity<Product> getProductById(@PathVariable Integer id);



}
