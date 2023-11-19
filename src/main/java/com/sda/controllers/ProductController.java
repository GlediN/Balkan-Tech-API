package com.sda.controllers;

import com.sda.dto.ProductDTO;
import com.sda.entities.Product;
import com.sda.services.ProductService;
import com.sda.utils.HelpfulUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path ="/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping(path = "/add")
    public ResponseEntity<String> addNewProduct(@RequestBody ProductDTO productDTO) {
        try {
            return productService.addNewProduct(productDTO);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return HelpfulUtils.getResponseEntity(HelpfulUtils.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(path = "/get")
    public ResponseEntity<List<Product>> getAllProduct() {
        try {
            return productService.getAllProduct();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(path = "/most-sold-products")
    public ResponseEntity<List<Product>> getMostSoldProducts() {
        return productService.getMostSoldProducts();
    }

    @PostMapping(path = "/update")
    public ResponseEntity<String> updateProduct(@RequestBody ProductDTO productDTO) {
        try {
            return productService.updateProduct(productDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HelpfulUtils.getResponseEntity(HelpfulUtils.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(path = "/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id) {
        try {
            return productService.deleteProduct(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HelpfulUtils.getResponseEntity(HelpfulUtils.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(path = "/updateQuantity")
    public ResponseEntity<String> updateQuantity(@RequestBody ProductDTO productDTO) {
        try {
            return productService.updateQuantity(productDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HelpfulUtils.getResponseEntity(HelpfulUtils.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(path = "/getByCategory/{id}")
    public ResponseEntity<List<Product>> getByCategory(@PathVariable Integer id) {
        try {
            return productService.getByCategory(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(path = "/getById/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
        try {
            return productService.getProductById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new Product(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(path = "/search/{search}")
    public ResponseEntity<List<Product>> searchProductByNameAndCategory(@PathVariable String search) {
        try {
            return productService.searchByProductNameAndCategory(search);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }



    }
