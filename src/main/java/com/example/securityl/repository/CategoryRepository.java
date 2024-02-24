package com.example.securityl.repository;

import com.example.securityl.model.Category;
import com.example.securityl.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category ,Integer> {
    boolean existsByName(String name);


}
