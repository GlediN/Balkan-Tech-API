package com.sda.serviceImpl;

import com.sda.dao.CategoryDao;
import com.sda.dao.ProductDao;
import com.sda.entities.Category;
import com.sda.entities.Product;
import com.sda.entities.ProductPhoto;
import com.sda.jwt.JwtFilter;
import com.sda.service.ProductService;
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
public class ProductServiceImpl implements ProductService {
    final ProductDao productDao;

    final CategoryDao categoryDao;

    final JwtFilter jwtFilter;


    @Override
    public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                if (validateProductMap(requestMap, false)) {
                    String productName = requestMap.get("name");
                    if (productDao.existsByName(productName)) {
                        return HelpfulUtils.getResponseEntity("Product with already exists", HttpStatus.OK);
                    }
                    Integer categoryId = Integer.parseInt(requestMap.get("categoryId"));
                    if (!productDao.existsCategoryById(categoryId)) {
                        return HelpfulUtils.getResponseEntity("Category does not exist. Create the category first.", HttpStatus.BAD_REQUEST);
                    }
                    productDao.save(getProductFromMap(requestMap, false));
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



    private boolean validateProductMap(Map<String, String> requestMap, boolean validateId) {
        if (requestMap.containsKey("name")) {
            if (requestMap.containsKey("id") && validateId) {
                return true;
            } else if (!validateId) {
                return true;
            }
        }

        return false;
    }

    private Product getProductFromMap(Map<String, String> requestMap, boolean isAdd) {
        Category category = new Category();
        category.setId(Integer.parseInt(requestMap.get("categoryId")));

        Product product = new Product();
        if (isAdd) {
            product.setId(Integer.parseInt(requestMap.get("id")));
        }
        if (!isAdd && requestMap.containsKey("quantity")) {
            product.setQuantity(Integer.parseInt(requestMap.get("quantity")));
        }
        product.setCategory(category);
        product.setName(requestMap.get("name"));
        product.setDescription(requestMap.get("description"));
        product.setPrice(Double.parseDouble(requestMap.get("price")));
        product.setDiscount(Double.parseDouble(requestMap.get("discount")));
        List<ProductPhoto> productPhotos = new ArrayList<>();
        if (requestMap.containsKey("productPhotos")) {
            String[] photoNames = requestMap.get("productPhotos").split(",");
            for (String photoName : photoNames) {
                ProductPhoto productPhoto = new ProductPhoto();
                productPhoto.setProductId(product);
                productPhoto.setProductPhoto(photoName);
                productPhotos.add(productPhoto);
            }
        }

        product.setProductPhotos(productPhotos);

        return product;
    }





    @Override
    public ResponseEntity<List<Product>> getAllProduct() {
        try {
            return new ResponseEntity<>(productDao.getAllProduct(), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                if (validateProductMap(requestMap, true)) {
                    Optional<Product> optional = productDao.findById(Integer.parseInt(requestMap.get("id")));
                    if (!optional.isEmpty()) {
                        Product product = getProductFromMap(requestMap, true);
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
        }
        return HelpfulUtils.getResponseEntity(HelpfulUtils.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteProduct(Integer id) {
        try {
            if (jwtFilter.isAdmin()) {
                Optional optional = productDao.findById(id);
                if (!optional.isEmpty()) {
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
        }
        return HelpfulUtils.getResponseEntity(HelpfulUtils.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateQuantity(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                Optional<Product> optional = productDao.findById(Integer.parseInt(requestMap.get("id")));
                if (!optional.isEmpty()) {
                    Integer currentQuantity = optional.get().getQuantity();
                    Integer updateQuantity = Integer.parseInt(requestMap.get("quantity"));
                    if (currentQuantity + updateQuantity >= 0) {
                        productDao.updateProductQuantity(updateQuantity, Integer.parseInt(requestMap.get("id")));
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
        }
        return HelpfulUtils.getResponseEntity(HelpfulUtils.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @Override
    public ResponseEntity<List<Product>> getByCategory(Integer id) {
        try {
            if (categoryDao.existsById(id)) {
                List<Product> products = productDao.getProductByCategory(id);
                return new ResponseEntity<>(products, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }






    @Override
    public ResponseEntity<Product> getProductById(Integer id) {
        try {
            return new ResponseEntity<>(productDao.getProductById(id), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return new ResponseEntity<>(new Product(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}