package com.sda.repositories;

import com.sda.entities.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface ProductDao extends JpaRepository<Product, Integer> {
    @Query("select p from Product p")
    List<Product> getAllProduct();

    @Query("select p from Product p order by p.soldQty desc")
    List<Product> getMostSoldProducts();

    @Modifying
    @Transactional
    @Query("update Product p set p.quantity = p.quantity + :quantity where p.id = :id")
    void updateProductQuantity(@Param("quantity") Integer quantity, @Param("id") Integer id);
    @Query("select p from Product p where p.category.id = :categoryId")
    List<Product> getProductByCategory(@Param("categoryId") Integer categoryId);
    Product getProductById(@Param("id") Integer id);
    boolean existsByName(String name);
    @Query("select count(c) > 0 from Category c where c.id = :categoryId")
    boolean existsCategoryById(@Param("categoryId") Integer categoryId);
    @Query("select p from Product p where p.name like %:search% or p.category.name like %:search%")
    List<Product> searchProductByNameOrCategory(@Param("search") String search);



}
