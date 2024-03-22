package com.example.securityl.services;



import com.example.securityl.Responses.ProductResponse;
import com.example.securityl.dtos.ProductDto;
import com.example.securityl.dtos.Top5ProductDto;
import com.example.securityl.exceptions.DataNotFoundException;
import com.example.securityl.models.Product;

import java.util.List;

public interface IProductService {
    Product getProductById(long id) throws Exception;
    Product createProduct(ProductDto productDto) throws DataNotFoundException;
    Product updateProduct(Long id, ProductDto productDto) throws Exception;
    List<ProductResponse> getProductByCategory(Long id);
    List<Product> getAllProduct();
    List<Top5ProductDto> getTop5BestSellingProducts();
}
