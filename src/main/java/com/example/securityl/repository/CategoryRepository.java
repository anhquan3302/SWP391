package com.example.securityl.repository;

import com.example.securityl.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category ,Integer> {
    boolean existsByName(String name);

    @Query("SELECT c FROM Category c WHERE c.name = :categoryName")
    Category findByName(String categoryName);

    List<Category> findAllByCategoryId(Integer categoryId);
}
