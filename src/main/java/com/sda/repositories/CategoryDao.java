package com.sda.repositories;

import com.sda.dto.CategoryDto;
import com.sda.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryDao extends JpaRepository<Category, Integer> {

    List<Category> findAll();

    boolean existsById(Integer id);

    boolean existsByName(String name);

    @Query("select c from Category c where c.parentId='null'")
    List<Category>getMainCategory();

    @Query("select c from Category c where c.parentId!='null'")
    List<Category>getSubCategory();


}