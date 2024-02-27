package com.example.securityl.repository;

import com.example.securityl.model.Products;
import jakarta.persistence.criteria.CriteriaBuilder;
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

    @Query("SELECT p.quantity FROM Products p WHERE p.productId = :productId")
    Integer findQuantityById(Integer productId);

    @Query("SELECT p FROM Products p WHERE " +
            "(:materials IS NULL OR p.materials = :materials) AND " +
            "(:brand IS NULL OR p.brand = :brand) AND " +
            "(:price IS NULL OR p.price = :price) AND " +
            "(:color IS NULL OR p.color = :color)")
    List<Products> findProductsByFilter(String materials, String brand, Double price, String color);

}
