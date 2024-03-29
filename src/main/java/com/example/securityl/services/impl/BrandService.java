package com.example.securityl.services.impl;


import com.example.securityl.converter.BrandConverter;
import com.example.securityl.dtos.BrandDto;
import com.example.securityl.models.Brand;
import com.example.securityl.repositories.BrandRepository;
import com.example.securityl.services.IBrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService implements IBrandService {
    private final BrandRepository brandRepository;
    @Override
    @Transactional
    public Brand createBrand(BrandDto brandDto) {
        String generatedCode = generateCodeFromName(brandDto.getName());
        Brand newBrand = Brand
                .builder()
                .name(brandDto.getName())
                .logo(brandDto.getLogo())
                .websiteUrl(brandDto.getWebsiteUrl())
                .code(generatedCode)
                .build();
        return brandRepository.save(newBrand);
    }

    private String generateCodeFromName(String name) {
        return name.replaceAll("\\s", "-").toLowerCase();
    }

    @Override
    @Transactional
    public Brand updateBrand(Long id, BrandDto brandDto) {
        String generatedCode = generateCodeFromName(brandDto.getName());
        Brand existingBrand = getBrandById(id);
        BrandConverter.toEntity(brandDto, existingBrand);
        existingBrand.setCode(generatedCode);
        existingBrand = brandRepository.save(existingBrand);
        return existingBrand;
    }
    @Override
    public List<Brand> getAllBrand() {
        return brandRepository.findAll();
    }

    @Override
    public Brand getBrandById(long id) {
        return brandRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Brand not found"));
    }


}
