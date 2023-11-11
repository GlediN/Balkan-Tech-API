package com.sda.serviceImpl;

import com.sda.dao.CategoryDao;
import com.sda.entities.Category;
import com.sda.jwt.JwtFilter;
import com.sda.service.CategoryService;
import com.sda.utils.HelpfulUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    final CategoryDao categoryDAO;

    final JwtFilter jwtFilter;
    @Override
    public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                if (validateCategoryMap(requestMap, false)) {
                    String categoryName = requestMap.get("name");
                    if(categoryDAO.existsByName(categoryName)){
                        return HelpfulUtils.getResponseEntity("Category already exists",HttpStatus.OK);
                    }
                    categoryDAO.save(getCategpryFromMap(requestMap, false));
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

    private boolean validateCategoryMap(Map<String, String> requestMap, boolean validateId) {
        if (requestMap.containsKey("name")) {
            if (requestMap.containsKey("id") && validateId) {
                return true;
            } else if (!validateId) {
                return true;
            }
        }
        return false;
    }

    private Category getCategpryFromMap(Map<String, String> requestMap, Boolean isAdd) {
        Category category = new Category();
        if (isAdd) {
            category.setId(Integer.parseInt(requestMap.get("id")));
        }
        category.setName(requestMap.get("name"));
        category.setPhoto(requestMap.get("photo"));
        category.setParentId(requestMap.get("parentId"));
        return category;
    }

    @Override
    public ResponseEntity<List<Category>> findAll() {
        try {
        {
                return new ResponseEntity<>(categoryDAO.findAll(), HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                if (validateCategoryMap(requestMap, true)) {
                    Optional<Category> optional = categoryDAO.findById(Integer.parseInt(requestMap.get("id")));
                    if (!optional.isEmpty()) {
                        categoryDAO.save(getCategpryFromMap(requestMap, true));
                        return HelpfulUtils.getResponseEntity("Category updated succesfully", HttpStatus.OK);

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

    @Override
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

}
