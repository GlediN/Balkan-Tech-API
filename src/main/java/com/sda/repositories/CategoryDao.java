package com.sda.repositories;

import com.sda.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryDao extends JpaRepository<Category, Integer> {

    List<Category> findAll();

    @Query("select c from Category c where c.parentId='null'")
    List<Category>getMainCategory();

    @Query("select c from Category c where c.parentId!='null'")
    List<Category>getSubCategory();

    boolean existsById(Integer id);

    boolean existsByName(String name);



}