package com.sda.rest;

import com.sda.entities.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RequestMapping(path = "/category")
public interface CategoryRest {
    @PostMapping(path = "/add")
    ResponseEntity<String> addNewCategory(@RequestBody Map<String, String> requestMap);

    @GetMapping(path = "/get")
    ResponseEntity<List<Category>> findAll();

    @PostMapping(path = "/update")
    ResponseEntity<String> updateCategory(@RequestBody
                                          Map<String, String> requestMap);

    @PostMapping(path = "/delete/{categoryId}")
    ResponseEntity<String> deleteCategory(@PathVariable Integer categoryId);


}
