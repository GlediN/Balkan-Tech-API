package com.sda.dao;

import com.sda.entities.Category;
import jakarta.persistence.NamedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryDao extends JpaRepository<Category, Integer> {
    @Query("select c from Category c where c.parentId='null' and c.id in (select p.category from Product p where p.quantity>0)")
    List<Category> getAllCategory();

    boolean existsById(Integer id);

    boolean existsByName(String name);



}
