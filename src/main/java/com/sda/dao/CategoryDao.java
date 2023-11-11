package com.sda.dao;

import com.sda.entities.Category;
import jakarta.persistence.NamedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryDao extends JpaRepository<Category, Integer> {

    List<Category> findAll();

    boolean existsById(Integer id);

    boolean existsByName(String name);



}
