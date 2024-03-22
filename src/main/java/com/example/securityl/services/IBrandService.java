package com.example.securityl.services;



import com.example.securityl.dtos.BrandDto;
import com.example.securityl.models.Brand;

import java.util.List;

public interface IBrandService {
    Brand createBrand (BrandDto brandDto);
    Brand updateBrand(Long id, BrandDto brandDto);
    Brand getBrandById(long id);
    List<Brand> getAllBrand();
}
