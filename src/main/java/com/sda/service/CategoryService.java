package com.sda.service;

import com.sda.entities.Category;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface CategoryService {

    ResponseEntity<String> addNewCategory(Map<String, String> requestMap);

    ResponseEntity<List<Category>> findAll();

    ResponseEntity<String> updateCategory(Map<String, String> requestMap);

    ResponseEntity<String> deleteCategory(Integer categoryId);

    ResponseEntity<List<Category>> getMainCategories();

    ResponseEntity<List<Category>> getSubCategories();
}