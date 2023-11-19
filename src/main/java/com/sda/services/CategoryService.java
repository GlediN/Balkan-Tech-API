package com.sda.services;

import com.sda.dto.CategoryDto;
import com.sda.entities.Category;
import com.sda.jwt.JwtFilter;
import com.sda.repositories.CategoryDao;
import com.sda.utils.HelpfulUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    final CategoryDao categoryDAO;
    final JwtFilter jwtFilter;

    public ResponseEntity<String> addNewCategory(CategoryDto categoryDto) {
        try {
            if (jwtFilter.isAdmin()) {
                if (validateCategoryDto(categoryDto, false)) {
                    String categoryName = categoryDto.getName();
                    if (categoryDAO.existsByName(categoryName)) {
                        return HelpfulUtils.getResponseEntity("Category already exists", HttpStatus.OK);
                    }
                    categoryDAO.save(getCategoryFromDto(categoryDto, false));
                    return HelpfulUtils.getResponseEntity("Category added successfully", HttpStatus.OK);
                }
            } else {
                return HelpfulUtils.getResponseEntity(HelpfulUtils.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HelpfulUtils.getResponseEntity(HelpfulUtils.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    public ResponseEntity<List<CategoryDto>> findAll() {
        try {
            List<Category> categories = categoryDAO.findAll();
            List<CategoryDto> categoryDtos = mapCategoriesToDtos(categories);
            return new ResponseEntity<>(categoryDtos, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> updateCategory(CategoryDto categoryDto) {
        try {
            if (jwtFilter.isAdmin()) {
                if (validateCategoryDto(categoryDto, true)) {
                    Optional<Category> optional = categoryDAO.findById(categoryDto.getId());
                    if (optional.isPresent()) {
                        categoryDAO.save(getCategoryFromDto(categoryDto, true));
                        return HelpfulUtils.getResponseEntity("Category updated successfully", HttpStatus.OK);
                    } else {
                        return HelpfulUtils.getResponseEntity("Category ID does not exist", HttpStatus.OK);
                    }
                }
                return HelpfulUtils.getResponseEntity(HelpfulUtils.INVALID_DATA, HttpStatus.BAD_REQUEST);
            } else {
                return HelpfulUtils.getResponseEntity(HelpfulUtils.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HelpfulUtils.getResponseEntity(HelpfulUtils.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    public ResponseEntity<String> deleteCategory(Integer categoryId) {
        try {
            if (jwtFilter.isAdmin()) {
                Optional<Category> optional = categoryDAO.findById(categoryId);
                if (optional.isPresent()) {
                    categoryDAO.deleteById(categoryId);
                    return HelpfulUtils.getResponseEntity("Category deleted successfully", HttpStatus.OK);
                } else {
                    return HelpfulUtils.getResponseEntity("Category ID does not exist", HttpStatus.NOT_FOUND);
                }
            } else {
                return HelpfulUtils.getResponseEntity(HelpfulUtils.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HelpfulUtils.getResponseEntity(HelpfulUtils.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<CategoryDto>> getMainCategories() {
        try {
            List<Category> mainCategories = categoryDAO.getMainCategory();
            List<CategoryDto> mainCategoryDtos = mapCategoriesToDtos(mainCategories);
            return new ResponseEntity<>(mainCategoryDtos, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<List<CategoryDto>> getSubCategories() {
        try {
            List<Category> subCategories = categoryDAO.getSubCategory();
            List<CategoryDto> subCategoryDtos = mapCategoriesToDtos(subCategories);
            return new ResponseEntity<>(subCategoryDtos, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean validateCategoryDto(CategoryDto categoryDto, boolean validateId) {
        if (validateId && categoryDto.getId() == null) {
            return false;
        }
        return categoryDto.getName() != null && categoryDto.getPhoto() != null && categoryDto.getParentId() != null;
    }

    private Category getCategoryFromDto(CategoryDto categoryDto, boolean isAdd) {
        Category category = new Category();
        if (isAdd) {
            category.setId(categoryDto.getId());
        }
        category.setName(categoryDto.getName());
        category.setPhoto(categoryDto.getPhoto());
        category.setParentId(categoryDto.getParentId());
        return category;
    }

    private List<CategoryDto> mapCategoriesToDtos(List<Category> categories) {
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Category category : categories) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId(category.getId());
            categoryDto.setName(category.getName());
            categoryDto.setPhoto(category.getPhoto());
            categoryDto.setParentId(category.getParentId());
            categoryDtos.add(categoryDto);
        }
        return categoryDtos;
    }

    public ResponseEntity<CategoryDto> getCategoryById(Integer categoryId) {
        try {
            Optional<Category> optionalCategory = categoryDAO.findById(categoryId);
            if (optionalCategory.isPresent()) {
                Category category = optionalCategory.get();
                CategoryDto categoryDto = mapCategoryToDto(category);
                return new ResponseEntity<>(categoryDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private CategoryDto mapCategoryToDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        categoryDto.setPhoto(category.getPhoto());
        categoryDto.setParentId(category.getParentId());
        return categoryDto;
    }


}
