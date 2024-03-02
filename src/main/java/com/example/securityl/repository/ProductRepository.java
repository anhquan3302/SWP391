package com.example.securityl.repository;

import com.example.securityl.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    Optional<Product> findProductByProductId(int productId);


    @Query("SELECT p.quantity FROM Product p WHERE p.productId = :productId")
    Integer findQuantityById(Integer productId);

    @Query("SELECT p FROM Product p WHERE " +
            "(:materials IS NULL OR p.materials = :materials) AND " +
            "(:brand IS NULL OR p.brand = :brand) AND " +
            "(:price IS NULL OR p.price = :price) AND " +
            "(:color IS NULL OR p.color = :color)")
    List<Product> findProductsByFilter(String materials, String brand, Double price, String color);


    @Query("SELECT p FROM Product p WHERE " +
            "(:materials IS NULL OR p.materials = :materials) AND " +
            "(:brand IS NULL OR p.brand = :brand) AND " +
            "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
            "(:color IS NULL OR p.color = :color)")
    List<Product> findProductsByFilter2(String materials, String brand, Double minPrice, Double maxPrice, String color);


    List<Product> findByFavorite(boolean favorite);

    boolean existsProductByProductName(String productName);
    Product findProductsByProductName(String productName);

}
