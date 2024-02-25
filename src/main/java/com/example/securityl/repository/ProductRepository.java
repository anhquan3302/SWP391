package com.example.securityl.repository;

import com.example.securityl.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Products,Integer> {
    Optional<Products> findProductByProductId(int productId);

    @Query("SELECT p FROM Products p JOIN p.categories c WHERE c.name = :categoryName")
    List<Products> findAllByCategoryName(String categoryName);


}
