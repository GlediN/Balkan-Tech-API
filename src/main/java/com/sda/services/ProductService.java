package com.sda.services;

import com.sda.dto.ProductDTO;
import com.sda.entities.Category;
import com.sda.entities.Product;
import com.sda.jwt.JwtFilter;
import com.sda.repositories.CategoryDao;
import com.sda.repositories.ProductDao;
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
public class ProductService {

    final ProductDao productDao;
    final CategoryDao categoryDao;
    final JwtFilter jwtFilter;


    public ResponseEntity<String> addNewProduct(ProductDTO productDTO) {
        try {
            if (jwtFilter.isAdmin()) {
                if (validateProductDTO(productDTO, false)) {
                    String productName = productDTO.getName();
                    if (productDao.existsByName(productName)) {
                        return HelpfulUtils.getResponseEntity("Product already exists", HttpStatus.OK);
                    }
                    Integer categoryId = productDTO.getCategoryId();
                    if (!productDao.existsCategoryById(categoryId)) {
                        return HelpfulUtils.getResponseEntity("Category does not exist. Create the category first.", HttpStatus.BAD_REQUEST);
                    }
                    productDao.save(getProductFromDTO(productDTO, false));
                    return HelpfulUtils.getResponseEntity("Product added successfully", HttpStatus.OK);
                }
                return HelpfulUtils.getResponseEntity(HelpfulUtils.INVALID_DATA, HttpStatus.BAD_REQUEST);
            } else {
                return HelpfulUtils.getResponseEntity(HelpfulUtils.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return HelpfulUtils.getResponseEntity(HelpfulUtils.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean validateProductDTO(ProductDTO productDTO, boolean validateId) {
        if (productDTO.getName() != null) {
            if (productDTO.getId() != null && validateId) {
                return true;
            } else if (!validateId) {
                return true;
            }
        }
        return false;
    }

    private Product getProductFromDTO(ProductDTO productDTO, boolean isAdd) {
        Category category = new Category();
        category.setId(productDTO.getCategoryId());

        Product product = new Product();
        if (isAdd) {
            product.setId(productDTO.getId());
        }
        if (!isAdd && productDTO.getQuantity() != null) {
            product.setQuantity(productDTO.getQuantity());
        }
        product.setCategory(category);
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setDiscount(productDTO.getDiscount());
        product.setImageUrl(productDTO.getImageUrl());
        return product;
    }


    public ResponseEntity<List<Product>> getAllProduct() {
        try {
            List<Product> products = productDao.findAll();
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<String> updateProduct(ProductDTO productDTO) {
        try {
            if (jwtFilter.isAdmin()) {
                if (validateProductDTO(productDTO, true)) {
                    Optional<Product> optional = productDao.findById(productDTO.getId());
                    if (optional.isPresent()) {
                        Product product = getProductFromDTO(productDTO, true);
                        product.setQuantity(optional.get().getQuantity());
                        productDao.save(product);
                        return HelpfulUtils.getResponseEntity("Product updated successfully", HttpStatus.OK);
                    } else {
                        return HelpfulUtils.getResponseEntity("Product does not exist", HttpStatus.OK);
                    }
                }
                return HelpfulUtils.getResponseEntity(HelpfulUtils.INVALID_DATA, HttpStatus.BAD_REQUEST);

            } else {
                return HelpfulUtils.getResponseEntity(HelpfulUtils.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return HelpfulUtils.getResponseEntity(HelpfulUtils.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<String> deleteProduct(Integer id) {
        try {
            if (jwtFilter.isAdmin()) {
                Optional<Product> optional = productDao.findById(id);
                if (optional.isPresent()) {
                    productDao.deleteById(id);
                    return HelpfulUtils.getResponseEntity("Product Deleted Successfully", HttpStatus.OK);
                } else {
                    return HelpfulUtils.getResponseEntity("Product ID does not exist", HttpStatus.OK);
                }
            } else {
                return HelpfulUtils.getResponseEntity(HelpfulUtils.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return HelpfulUtils.getResponseEntity(HelpfulUtils.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> updateQuantity(ProductDTO productDTO) {
        try {
            if (jwtFilter.isAdmin()) {
                Optional<Product> optional = productDao.findById(productDTO.getId());
                if (optional.isPresent()) {
                    Integer currentQuantity = optional.get().getQuantity();
                    Integer updateQuantity = productDTO.getQuantity();
                    if (currentQuantity + updateQuantity >= 0) {
                        productDao.updateProductQuantity(updateQuantity, productDTO.getId());
                        return HelpfulUtils.getResponseEntity("Product Quantity Updated Successfully", HttpStatus.OK);
                    } else {
                        return HelpfulUtils.getResponseEntity("Invalid quantity. Resulting quantity cannot be negative.", HttpStatus.BAD_REQUEST);
                    }
                }
                return HelpfulUtils.getResponseEntity("Product does not exist", HttpStatus.OK);
            } else {
                return HelpfulUtils.getResponseEntity(HelpfulUtils.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return HelpfulUtils.getResponseEntity(HelpfulUtils.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Product>> getByCategory(Integer id) {
        try {
            if (categoryDao.existsById(id)) {
                List<Product> products = productDao.getProductByCategory(id);
                return new ResponseEntity<>(products, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<Product> getProductById(Integer id) {
        try {
            return new ResponseEntity<>(productDao.getProductById(id), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Product(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<List<Product>> getMostSoldProducts() {
        try {
            return new ResponseEntity<>(productDao.getMostSoldProducts(), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<List<Product>> searchByProductNameAndCategory(String search) {
        try {
            return new ResponseEntity<>(productDao.searchProductByNameOrCategory(search), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
    }

}
