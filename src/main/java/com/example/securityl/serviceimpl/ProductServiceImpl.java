package com.example.securityl.serviceimpl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.securityl.model.Category;

import com.example.securityl.model.Enum.Role;
import com.example.securityl.model.ImageProduct;
import com.example.securityl.model.Product;
import com.example.securityl.repository.*;
import com.example.securityl.response.ProductResponse.ListProductResponse;
import com.example.securityl.response.ProductResponse.ProductResponse;
import com.example.securityl.request.ProductRequest.RequestObject;
import com.example.securityl.request.ProductRequest.SearchProduct;
import com.example.securityl.response.CategoryResponse.CategoryResponse;
import com.example.securityl.response.ProductResponse.ResponseObject;
import com.example.securityl.service.JwtService;
import com.example.securityl.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final Cloudinary cloudinary;
    private final CategoryRepository categoryRepository;

    @Override
    public ResponseEntity<ResponseObject> createProduct(String productName, String title, String description, double discount, String color, String size, double price, String material, String thumbnail, Integer quantity, String brand, boolean favorite, String categoryName) {
        try {
            Date date = new Date();
            String token = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                    .getRequest()
                    .getHeader("Authorization")
                    .substring(7);
            String userEmail = jwtService.extractUsername(token);
            var requester = userRepository.findUserByEmail(userEmail).orElse(null);
            if (requester == null || !(requester.getRole().equals(Role.staff) || requester.getRole().equals(Role.admin))) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseObject("Fail", "Unauthorized",null));
            }
            if (size == null || size.trim().isEmpty() || productName == null || productName.trim().isEmpty() || description == null || description.trim().isEmpty() || title == null || title.trim().isEmpty() || price <= 0) {
                return ResponseEntity.badRequest().body(new ResponseObject("Fail", "Invalid request body",null));
            }
            if(quantity <= 0 || quantity > 10){
                return ResponseEntity.status(400).body(new ResponseObject("Fail","Quantity does not exist",null));
            }
            if (!(requester.getRole().equals(Role.admin) || requester.getRole().equals(Role.staff))) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseObject("Fail", "Forbidden",null));
            }
            Category category1 = categoryRepository.findByName(categoryName);
            if (category1 != null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Fail", "Category not found", null));
            }

            List<Category> categories = new ArrayList<>();
            categories.add(category1);
            Product product = Product.builder()
                    .thumbnail(thumbnail)
                    .productName(productName.trim())
                    .description(description.trim())
                    .title(title.trim())
                    .color(color)
                    .discount(discount)
                    .createdAt(date)
                    .updatedAt(date)
                    .materials(material)
                    .size(size)
                    .price(price)
                    .brand(brand)
                    .quantity(quantity)
                    .categories(categories)
                    .favorite(favorite)
                    .user(userRepository.findUserIdByEmail(userEmail))
                    .build();

            Product savedProduct = productRepository.save(product);
            return ResponseEntity.ok(new ResponseObject("Success", "Product created successfully", convertToProductResponse(savedProduct)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseObject("Fail", "Internal server error",null));
        }
    }


    private ProductResponse convertToProductResponse(Product savedProduct) {
        return ProductResponse.builder()
                .productId(savedProduct.getProductId())
                .title(savedProduct.getTitle())
                .discount(savedProduct.getDiscount())
                .description(savedProduct.getDescription())
                .createdAt(savedProduct.getCreatedAt())
                .updatedAt(savedProduct.getUpdatedAt())
                .size(savedProduct.getSize())
                .productName(savedProduct.getProductName())
                .thumbnail(savedProduct.getThumbnail())
                .favorite(savedProduct.isFavorite())
                .quantity(savedProduct.getQuantity())
                .color(savedProduct.getColor())
                .price(savedProduct.getPrice())
                .materials(savedProduct.getMaterials())
                .brand(savedProduct.getBrand())
                .imageProducts(savedProduct.getImageProducts())
                .build();
    }

    private List<CategoryResponse> convertToCategoryResponseList(List<Category> categories) {
        List<CategoryResponse> categoryResponses = new ArrayList<>();
        for (Category category : categories) {
            CategoryResponse categoryResponse = convertToCategoryResponse(category);
            categoryResponses.add(categoryResponse);
        }
        return categoryResponses;
    }

    private CategoryResponse convertToCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .categoryId(category.getCategoryId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }







    @Override
    public ResponseEntity<ResponseObject> updateProduct(
            Integer productId, RequestObject requestObject) {
        try {
            String token = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                    .getRequest()
                    .getHeader("Authorization")
                    .substring(7);
            String userEmail = jwtService.extractUsername(token);
            var requester = userRepository.findUserByEmail(userEmail).orElse(null);
            if (requester == null) {
                return ResponseEntity.status(404).body(new ResponseObject("Fail", "User not found", null));
            }
            if (requestObject.getSize() == null || requestObject.getSize().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(new ResponseObject("Fail", "Size must be greater than 0", null));
            }
            if (requestObject.getDescription() == null || requestObject.getDescription().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(new ResponseObject("Fail", "Description is empty", null));
            }
            if (requestObject.getTitle() == null || requestObject.getTitle().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(new ResponseObject("Fail", "Title is empty", null));
            }
            if (requestObject.getPrice() <= 0) {
                return ResponseEntity.badRequest().body(new ResponseObject("Fail", "Price must be greater than 0", null));
            }
            if (!(requester.getRole().equals(Role.admin) || requester.getRole().equals(Role.staff))) {
                return ResponseEntity.status(403).body(new ResponseObject("Fail", "You don't have permission", null));
            }
            if(requestObject.getBrand()  == null || requestObject.getBrand().trim().isEmpty() ){
                return ResponseEntity.badRequest().body(new ResponseObject("Fail", "Brand is empty", null));

            }
            if (requestObject.getQuantity() <= 0 || requestObject.getQuantity() > 10) {
                return ResponseEntity.badRequest().body(new ResponseObject("Fail", "Quantity must be greater than 0 and < 10", null));
            }
            var checkProduct = productRepository.findById(productId).orElse(null);
            if (checkProduct != null) {
                checkProduct.setThumbnail(requestObject.getThumbnail());
                checkProduct.setSize(requestObject.getSize());
                checkProduct.setDescription(requestObject.getDescription().trim());
                checkProduct.setTitle(requestObject.getTitle().trim());
                checkProduct.setPrice(requestObject.getPrice());
                checkProduct.setUpdatedAt(new Date());
                checkProduct.setQuantity(requestObject.getQuantity());
                checkProduct.setBrand(requestObject.getBrand());
                var updateProduct = productRepository.save(checkProduct);
                return ResponseEntity.ok(new ResponseObject("Success", "Update product success", convertToProductResponse(updateProduct)));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseObject("Fail", "Internal Server Error", null));
        }
        return ResponseEntity.status(500).body(ResponseObject.builder()
                .status("Fail")
                .message("Internal sever error ")
                .productResponse(null)
                .build());
    }

    @Override
    public ProductResponse getProductById(Integer productId) {
        try {
            Optional<Product> productOptional = productRepository.findProductByProductId(productId);
            if (productOptional.isPresent()) {
                Product product = productOptional.get();
                return convertToProductResponse(product);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }







    @Override
    public ResponseEntity<ResponseObject> deleteProduct(Integer productId) {
        var checkProduct = productRepository.findProductByProductId(productId).orElse(null);
        if (checkProduct != null) {
            productRepository.delete(checkProduct);
            return ResponseEntity.ok(new ResponseObject("Success", "Delete successful", convertToProductResponse(checkProduct)));
        } else {
            return ResponseEntity.status(404).body(new ResponseObject("Fail", "Delete fail", null));
        }
    }

    @Override
    public ResponseEntity<ListProductResponse> getAll() {
        try {
            var listProduct = productRepository.findAll();
            List<ProductResponse> productResponseList = convertToProductResponseList(listProduct);
            return ResponseEntity.ok(new ListProductResponse("Success", "List all product", productResponseList));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ListProductResponse("Fail", "Not find list product", null));
        }
    }




    private List<ProductResponse> convertToProductResponseList(List<Product> productList) {
        List<ProductResponse> productResponseList = new ArrayList<>();
        for (Product product : productList) {
            ProductResponse productResponse = convertToProductResponse(product);
            productResponseList.add(productResponse);
        }
        return productResponseList;
    }





    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        return (String) uploadResult.get("url");
    }


    @Override
    public void uploadProductImage(Integer productId, List<String> imageUrls) {
        try {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));
            List<ImageProduct> imageProducts = new ArrayList<>();
            for (String imageUrl : imageUrls) {
                ImageProduct imageProduct = new ImageProduct();
                imageProduct.setImageUrl(imageUrl);
                imageProduct.setProduct(product);
                imageProducts.add(imageProduct);
            }
            product.setImageProducts(imageProducts);
            productRepository.save(product);
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload product images: " + e.getMessage(), e);
        }
    }

    @Override
    public ResponseEntity<ListProductResponse> searchProduct(SearchProduct searchProduct) {
        try {
            List<Product> productsList = productRepository.findAll();
            List<Product> showProduct = new ArrayList<>();

            if (searchProduct.getProductId() != null) {
                showProduct = productsList.stream()
                        .filter(n -> n.getProductId().equals(searchProduct.getProductId()))
                        .collect(Collectors.toList());
            } else if (searchProduct.getProductName() != null && !searchProduct.getProductName().trim().isEmpty()) {
                String productName = searchProduct.getProductName().toLowerCase();
                showProduct = productsList.stream()
                        .filter(n -> n.getProductName().toLowerCase().contains(productName))
                        .collect(Collectors.toList());
            }

            List<ProductResponse> list = convertToProductResponseList(showProduct);
            return ResponseEntity.ok(new ListProductResponse("Success", "List products", list));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ListProductResponse("Fail", "Not found product", null));
        }
    }


    @Override
    public List<Product> searchProducts(String materials, String brand, Double price, String color) {
        if (materials == null && brand == null && price == null && color == null) {
            return productRepository.findAll();
        } else {
            return productRepository.findProductsByFilter(materials, brand, price, color);
        }
    }

    public List<Product> searchProductsVer2(String materials, String brand, Double minPrice, Double maxPrice, String color) {

            return productRepository.findProductsByFilter2(materials, brand, minPrice, maxPrice, color);


    }

    @Override
    public ResponseEntity<ListProductResponse> viewWishList(boolean favorite) {
        List<Product> wishlistProducts = productRepository.findByFavorite(favorite);
        List<ProductResponse> response = convertToProductResponseList(wishlistProducts);
        return ResponseEntity.ok().body(new ListProductResponse("Success","List wish list",response));
    }




}

