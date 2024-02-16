package com.example.securityl.repository;

import com.example.securityl.model.ImageProduct;
import com.example.securityl.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageProductRepository extends JpaRepository<ImageProduct,Integer> {
}
