package com.sda.restImpl;

import com.sda.entities.Product;
import com.sda.rest.ProductRest;
import com.sda.service.ProductService;
import com.sda.utils.HelpfulUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@RequiredArgsConstructor
@RestController
public class ProductRestImpl implements ProductRest {
    final ProductService productService;

    @Override
    public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
        try{
            return productService.addNewProduct(requestMap);

        }catch (Exception e){
            e.printStackTrace();
        }
        return HelpfulUtils.getResponseEntity(HelpfulUtils.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Product>> getAllProduct() {
        try{
            return productService.getAllProduct();
        }catch (Exception e){
            e.printStackTrace();
        }



        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);



    }

    @Override
    public ResponseEntity<List<Product>> getMostSoldProducts() {
        return productService.getMostSoldProducts();
    }

    @Override
    public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
        try{
            return productService.updateProduct(requestMap);

        }catch (Exception e){
            e.printStackTrace();
        }
        return HelpfulUtils.getResponseEntity(HelpfulUtils.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);


    }

    @Override
    public ResponseEntity<String> deleteProduct(Integer id) {
        try{
            return productService.deleteProduct(id);

        }catch (Exception e){
            e.printStackTrace();
        }
        return  HelpfulUtils.getResponseEntity(HelpfulUtils.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateQuantity(Map<String, String> requestMap) {

        try {
            return productService.updateQuantity(requestMap);

        } catch (Exception e){
            e.printStackTrace();
        }



        return HelpfulUtils.getResponseEntity(HelpfulUtils.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);



    }

    @Override
    public ResponseEntity<List<Product>> getByCategory(Integer id) {
        try{
            return productService.getByCategory(id);


        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);



    }

    @Override
    public ResponseEntity<Product> getProductById(Integer id) {
        try{
            return productService.getProductById(id);

        }catch (Exception e){
            e.printStackTrace();
        }


        return new ResponseEntity<>(new Product(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Product>> searchProductByNameAndCategory(String search) {
        try{
            return productService.searchByProductNameAndCategory(search);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
