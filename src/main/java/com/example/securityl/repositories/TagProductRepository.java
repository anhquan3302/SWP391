package com.example.securityl.repositories;

import com.example.securityl.models.TagsProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagProductRepository extends JpaRepository<TagsProduct, Long> {

}
