package com.example.securityl.services;


import com.example.securityl.dtos.ProductImageDto;
import com.example.securityl.models.ProductImages;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProductImageService {
    //ProductImages createProductImage(Long productId, ProductImageDto productImageDTO, MultipartFile file) throws Exception;
    List<ProductImageDto> uploadFiles(List<MultipartFile> gifs, Long productId);
    //List<ProductImageDto> updateFiles(List<MultipartFile> newImages, Long productId);
    List<ProductImages> getImagesByProduct (Long id);
}
