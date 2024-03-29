package com.example.securityl.services.impl;


import com.example.securityl.Responses.ProductResponse;
import com.example.securityl.converter.ProductConverter;
import com.example.securityl.dtos.ProductDto;
import com.example.securityl.dtos.ProductImageDto;
import com.example.securityl.dtos.Top5ProductDto;
import com.example.securityl.exceptions.DataNotFoundException;
import com.example.securityl.models.*;
import com.example.securityl.repositories.*;
import com.example.securityl.services.IProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final TagProductRepository tagProductRepository;
    private final FeedbackRepository feedbackRepository;


    private final OrderDetailRepository orderDetailRepository;



    private final ProductImageRepository productImageRepository;

    private final ModelMapper modelMapper;


    @Override
    public Product getProductById(long id) throws Exception {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isPresent()) {
            return optionalProduct.get();
        }
        throw new DataNotFoundException("Cannot find product with id =" + id);
    }

    @Override
    @Transactional
    public Product createProduct(ProductDto productDto) throws DataNotFoundException {
        // Tạo mã từ tên sản phẩm
        String generatedCode = generateCodeFromName(productDto.getName());

        // Lấy thông tin về category, brand và tagsProduct từ ID
        Category existingCategory = categoryRepository
                .findById(productDto.getCategoryId())
                .orElseThrow(() ->
                        new DataNotFoundException(
                                "Cannot find category with id: " + productDto.getCategoryId()));
        Brand existingBrand = brandRepository
                .findById(productDto.getBrandId())
                .orElseThrow(() ->
                        new DataNotFoundException(
                                "Cannot find category with id: " + productDto.getBrandId()));
        TagsProduct existingProductTag = tagProductRepository
                .findById(productDto.getTagsProductId())
                .orElseThrow(() ->
                        new DataNotFoundException(
                                "Cannot find category with id: " + productDto.getTagsProductId()));

        // Tạo đối tượng Product từ DTO
        Product product = ProductConverter.toEntity(productDto);

        // Tính giá và mã giảm giá
        double discount = productDto.getDiscount() != null ? productDto.getDiscount() : 0.0;
        double priceSale = product.getPriceSale();
        double price = priceSale * ((100 - discount) / 100);
        product.setPrice(price);
        product.setCodeProduct(generatedCode);
        product.setCategory(existingCategory);
        product.setBrand(existingBrand);
        product.setTagsProduct(existingProductTag);

        // Lưu thông tin sản phẩm vào cơ sở dữ liệu
        product = productRepository.save(product);

        // Lưu thông tin ảnh sản phẩm vào cơ sở dữ liệu và gán ảnh đầu tiên vào thumbnail
        if (productDto.getProductImages() != null && !productDto.getProductImages().isEmpty()) {
            List<ProductImages> productImages = new ArrayList<>();
            List<ProductImageDto> newImages = productDto.getProductImages();

            // Kiểm tra số lượng hình ảnh hiện tại của sản phẩm
            List<ProductImages> existingImages = product.getProductImages();
            int currentImageCount = (existingImages != null) ? existingImages.size() : 0;
            int maxImageCount = 5;

            // Kiểm tra xem số lượng hình ảnh mới có vượt quá giới hạn không
            int newImageCount = Math.min(maxImageCount - currentImageCount, newImages.size());

            // Nếu số lượng hình ảnh mới vượt quá giới hạn, báo lỗi và không lưu vào cơ sở dữ liệu
            if (newImages.size() > newImageCount) {
                throw new DataNotFoundException("Exceeded maximum allowed images");
            }

            // Thêm hình ảnh mới vào sản phẩm và lấy ảnh đầu tiên để làm thumbnail
            String firstImageUrl = null;
            for (int i = 0; i < newImageCount; i++) {
                ProductImageDto imageDto = newImages.get(i);
                ProductImages productImage = new ProductImages();
                productImage.setProduct(product);
                productImage.setImageUrl(imageDto.getImageUrl());
                productImages.add(productImage);
                // Lấy ảnh đầu tiên để làm thumbnail
                if (i == 0) {
                    firstImageUrl = imageDto.getImageUrl();
                }
            }

            // Gán ảnh đầu tiên vào thumbnail
            product.setThumbnail(firstImageUrl);

            // Lưu thông tin ảnh sản phẩm và sản phẩm vào cơ sở dữ liệu
            productImageRepository.saveAll(productImages);
            productRepository.save(product);
        }

        return product;
    }

    @Override
    @Transactional
    public Product updateProduct(Long id, ProductDto productDto) throws Exception {
        Product existingProduct = getProductById(id);
        if (existingProduct != null) {
            String generatedCode = generateCodeFromName(productDto.getName());
            Category existingCategory = categoryRepository
                    .findById(productDto.getCategoryId())
                    .orElseThrow(() ->
                            new DataNotFoundException(
                                    "Cannot find category with id: " + productDto.getCategoryId()));
            Brand existingBrand = brandRepository
                    .findById(productDto.getBrandId())
                    .orElseThrow(() ->
                            new DataNotFoundException(
                                    "Cannot find category with id: " + productDto.getBrandId()));
            TagsProduct existingProductTag = tagProductRepository
                    .findById(productDto.getTagsProductId())
                    .orElseThrow(() ->
                            new DataNotFoundException(
                                    "Cannot find category with id: " + productDto.getTagsProductId()));
            ProductConverter.toEntity(productDto, existingProduct);
            double discount = productDto.getDiscount() != null ? productDto.getDiscount() : 0.0;
            double priceSale = productDto.getPriceSale();
            double price = priceSale * ((100 - discount) / 100);
            existingProduct.setPrice(price);
            existingProduct.setCodeProduct(generatedCode);
            existingProduct.setCategory(existingCategory);
            existingProduct.setBrand(existingBrand);
            existingProduct.setTagsProduct(existingProductTag);
            existingProduct = productRepository.save(existingProduct);

            if (productDto.getProductImages() != null && !productDto.getProductImages().isEmpty()) {
                String firstImageUrl = null;
                // Lưu trữ ảnh cũ
                List<ProductImages> existingImages = existingProduct.getProductImages();
                for (ProductImageDto imageDto : productDto.getProductImages()) {
                    if (imageDto.getId() != null) {
                        // Nếu có id, cập nhật hình ảnh
                        ProductImages existingImage = productImageRepository.findById(imageDto.getId())
                                .orElseThrow(() -> new DataNotFoundException("Image not found with id: " + imageDto.getId()));
                        // Kiểm tra nếu ảnh mới là null hoặc rỗng, sử dụng ảnh cũ
                        if (imageDto.getImageUrl() == null || imageDto.getImageUrl().isEmpty()) {
                            imageDto.setImageUrl(existingImage.getImageUrl());
                        } else {
                            existingImage.setImageUrl(imageDto.getImageUrl());
                        }
                        productImageRepository.save(existingImage);
                        // Cập nhật thumbnail nếu chưa có ảnh thumbnail
                        if (firstImageUrl == null) {
                            firstImageUrl = imageDto.getImageUrl();
                        }
                    } else {
                        // Nếu không có id, thêm hình ảnh mới
                        ProductImages newImage = new ProductImages();
                        newImage.setProduct(existingProduct);
                        // Kiểm tra nếu ảnh mới là null hoặc rỗng, sử dụng ảnh cũ
                        if (imageDto.getImageUrl() == null || imageDto.getImageUrl().isEmpty()) {
                            // Kiểm tra xem có ảnh cũ không
                            if (existingImages != null && !existingImages.isEmpty()) {
                                // Sử dụng ảnh cũ
                                newImage.setImageUrl(existingImages.get(0).getImageUrl());
                            } else {
                                // Không có ảnh cũ, không thay đổi
                                continue;
                            }
                        } else {
                            newImage.setImageUrl(imageDto.getImageUrl());
                        }
                        productImageRepository.save(newImage);
                        // Cập nhật thumbnail nếu chưa có ảnh thumbnail
                        if (firstImageUrl == null) {
                            firstImageUrl = newImage.getImageUrl();
                        }
                    }
                }
                // Cập nhật thumbnail nếu chưa có ảnh thumbnail
                if (firstImageUrl != null) {
                    existingProduct.setThumbnail(firstImageUrl);
                    existingProduct = productRepository.save(existingProduct);
                }
            }

            return existingProduct;
        }
        return null;
    }


    public Page<ProductResponse> getAllProducts(String keyword, PageRequest pageRequest,
                                                Double minPrice, Double maxPrice,
                                                List<Long> brandIds, List<Long> tagsProductIds, List<Long> categoryIds) {
        Page<Product> products;
        products = productRepository.searchProducts(
                keyword, pageRequest, minPrice, maxPrice, brandIds, tagsProductIds, categoryIds);
        return products.map(product -> {
            ProductResponse response = ProductConverter.toResponse(product);
            Double averageRating = feedbackRepository.findAverageRatingByProductId(product.getId());
            response.setRating(averageRating);
            return response;
        });
    }

    public List<ProductResponse> getAll(){
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> {
                    ProductResponse response = ProductConverter.toResponse(product);
                    Double averageRating = feedbackRepository.findAverageRatingByProductId(product.getId());
                    response.setRating(averageRating);
                    return response;
                })
                .collect(Collectors.toList());

    }
    public List<ProductResponse> getProductByCategory(Long id){
        List<Product> products = productRepository.findByCategoryId(id);
        return products.stream()
                .map(product -> {
                    ProductResponse response = ProductConverter.toResponse(product);
                    Double averageRating = feedbackRepository.findAverageRatingByProductId(product.getId());
                    response.setRating(averageRating);
                    return response;
                })
                .collect(Collectors.toList());
    }
    public List<Product> getAllProduct(){
        return productRepository.findAll();
    }

    @Override
    public List<Top5ProductDto> getTop5BestSellingProducts() {
        List<Object[]> topProducts = orderDetailRepository.findTop5BestSellingProducts();
        return topProducts.stream()
                .map(this::mapToProductDto)
                .collect(Collectors.toList());
    }


//    @Override
//    public List<Product> findtop5() {
//        return null;
//    }


//    @Override
//    public ResponseEntity<ProductListFavorite> findTopFavoriteProducts() {
//        List<Product> productList = productRepository.findTopFavoriteProducts();
//
//        List<ProductResponse> productResponseList = new ArrayList<>();
//        for (Product product : productList) {
//            ProductResponse productResponse = new ProductResponse();
//            productResponse.setId(product.getId());
//            productResponse.setProductImages(product.getProductImages());
//            productResponse.setPrice(product.getPrice());
//            productResponse.setDescription(product.getDescription());
//            productResponse.setThumbnail(product.getThumbnail());
//            productResponse.setCategoryId(product.getCategory());
//            productResponse.setDiscount(product.getDiscount());
//            productResponse.setCreatedAt(LocalDateTime.now());
//            productResponse.setUpdatedAt(LocalDateTime.now());
//            productResponse.setMaterial(product.getMaterial());
//            productResponse.setName(product.getName());
//            productResponse.setSize(product.getSize());
//            productResponse.setBrandId(product.getBrand());
//            productResponse.setCodeProduct(product.getCodeProduct());
//            productResponseList.add(productResponse);
//        }
//
//        ProductListFavorite productListFavorite = ProductListFavorite.builder()
//                .productResponseList(productResponseList)
//                .message("List wish list")
//                .build();
//
//        return ResponseEntity.ok().body(productListFavorite);
//    }

//    @Override
//    public List<ProductDto> findTop5FavoriteProducts() {
//        List<Product> top5FavoriteProducts = productRepository.findTop5FavoriteProducts();
//        List<ProductDto> top5FavoriteProductDtos = new ArrayList<>();
//
//        for (Product product : top5FavoriteProducts) {
//            ProductDto productDto = convertToDto(product);
//            top5FavoriteProductDtos.add(productDto);
//        }
//
//        return top5FavoriteProductDtos;
//    }

    private ProductDto convertToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setThumbnail(product.getThumbnail());
        productDto.setPrice(product.getPrice());
        productDto.setPriceSale(product.getPriceSale());
        productDto.setQuantity(product.getQuantity());
        productDto.getProductImages();
        productDto.setSize(product.getSize());
        productDto.setColor(product.getColor());
        productDto.setCodeProduct(product.getCodeProduct());
        productDto.setStatus(product.getStatus());
        productDto.setDiscount(product.getDiscount());
        productDto.setCategoryId(product.getCategory().getId());
        productDto.setBrandId(product.getBrand().getId());
        productDto.setTagsProductId(product.getTagsProduct().getId());
        // Add mapping for productImages if needed
        return productDto;
    }


//    @Override
//    public ResponseEntity<List<Product>> findProductFavorite() {
//        try {
//            List<Product> productList = productRepository.findAll();
//            List<Product> favoriteProducts = productList.stream()
//                    .filter(Product::isFavorite )
//                    .collect(Collectors.toList());
//            return ResponseEntity.ok(favoriteProducts);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }


    private Top5ProductDto mapToProductDto(Object[] result) {
        Top5ProductDto productDto = new Top5ProductDto();
        productDto.setProduct((Product) result[0]);
        productDto.setTotalQuantitySold(((Long) result[1]).intValue()); // Chuyển đổi Long sang Integer
        productDto.setTotalAmountSold((Double) result[2]);
        return productDto;
    }

//    public List<Product> getTop5Products() {
//        return productRepository.findTop5ByOrderByQuantitySoldDesc();
//    }



    private String generateCodeFromName(String codeProduct) {
        return codeProduct.replaceAll("\\s", "-").toLowerCase();
    }
}
