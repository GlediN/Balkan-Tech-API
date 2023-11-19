package com.sda.controllers;

import com.sda.dto.CategoryDto;
import com.sda.services.CategoryService;
import com.sda.utils.HelpfulUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/category")
public class CategoryController {

    final CategoryService categoryService;

    @PostMapping(path = "/add")
    public ResponseEntity<String> addNewCategory(@RequestBody CategoryDto categoryDto) {
        try {
            return categoryService.addNewCategory(categoryDto);
        } catch (Exception e) {
            e.printStackTrace();
            return HelpfulUtils.getResponseEntity(HelpfulUtils.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/get")
    public ResponseEntity<List<CategoryDto>> findAll() {
        try {
            return categoryService.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/update")
    public ResponseEntity<String> updateCategory(@RequestBody CategoryDto categoryDto) {
        try {
            return categoryService.updateCategory(categoryDto);
        } catch (Exception e) {
            e.printStackTrace();
            return HelpfulUtils.getResponseEntity(HelpfulUtils.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/delete/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Integer categoryId) {
        try {
            return categoryService.deleteCategory(categoryId);
        } catch (Exception e) {
            e.printStackTrace();
            return HelpfulUtils.getResponseEntity(HelpfulUtils.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/main-category")
    public ResponseEntity<List<CategoryDto>> getMainCategories() {
        try {
            return categoryService.getMainCategories();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sub-category")
    public ResponseEntity<List<CategoryDto>> getSubCategories() {
        try {
            return categoryService.getSubCategories();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer categoryId) {
        try {
            return categoryService.getCategoryById(categoryId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
