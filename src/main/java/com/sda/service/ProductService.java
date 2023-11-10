package com.sda.service;

import com.sda.entities.Product;
import com.sda.wrapper.ProductWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ProductService {

    ResponseEntity<String> addNewProduct(Map<String, String> requestMap);


    ResponseEntity<List<ProductWrapper>> getAllProduct();

    ResponseEntity<String> updateProduct(Map<String, String> requestMap);

    ResponseEntity<String> deleteProduct(Integer id);

    ResponseEntity<String> updateQuantity(Map<String, String> requestMap);

    ResponseEntity<List<Product>> getByCategory(Integer id);

    ResponseEntity<ProductWrapper> getProductById(Integer id);




}
