package com.example.securityl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.securityl.model.Category;
import com.example.securityl.model.Enum.Role;
import com.example.securityl.model.Product;
import com.example.securityl.model.User;
import com.example.securityl.repository.CategoryRepository;
import com.example.securityl.repository.ProductRepository;
import com.example.securityl.request.UserRequest.RegisterRequest;
import com.example.securityl.service.AuthenticationService;
import com.example.securityl.service.UserService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class SecurityLApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityLApplication.class, args);
    }



    @Bean
    public Cloudinary CloudinaryConfig(){
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dxorh7ue1",
                "api_key", "547243734762266",
                "api_secret", "YbPOvxckYbyokrSl2z1Wci6nRCI"));

        return cloudinary;
    }

    @Bean
    public CommandLineRunner commandLineRunner(
            AuthenticationService service,
            UserService userService,
            ProductRepository productRepository,
            CategoryRepository categoryRepository
    ) {
        return args -> {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            RegisterRequest adminRequest = RegisterRequest.builder()
                    .name("Admin")
                    .address("Viet Nam")
                    .phone("0937534654")
                    .email("admin@gmail.com")
                    .password("admin")
                    .role(Role.admin)
                    .build();
            service.register(adminRequest);


            RegisterRequest staff = RegisterRequest.builder()
                    .name("Staff")
                    .address("Viet Nam")
                    .phone("0937534654")
                    .email("staff@gmail.com")
                    .password("staff")
                    .role(Role.staff)
                    .build();
            service.register(staff);
            User adminUser = userService.findByEmailForMail(adminRequest.getEmail());


            Product product2 = Product.builder()
                    .productName("Bed")
                    .title("Japanese-style box bed GN-26")
                    .price(240000)
                    .size("51H x 78W x 83D")
                    .color("white")
                    .materials("Hardwood Pine")
                    .description("THE INSIDE Slip covered Bed, Indigo Fritz, King")
                    .createdAt(dateFormat.parse("2024-02-24"))
                    .updatedAt(dateFormat.parse("2024-02-24"))
                    .discount(20)
                    .quantity(7)
                    .brand("Japan")
                    .favorite(true)
                    .user(adminUser)
                    .thumbnail("https://bizweb.dktcdn.net/100/467/207/products/screenshot-2023-09-22-121711.jpg?v=1696500727497")
                    .build();
            productRepository.save(product2);
            Product product4 = Product.builder()
                    .productName("Bon tam")
                    .title("Bon tam thong minh 2024")
                    .price(210000)
                    .size("693Dx380Wx765D")
                    .color("white")
                    .materials("Su")
                    .description("THE INSIDE Traditional Accent Sofa,Espresso")
                    .createdAt(dateFormat.parse("2024-03-24"))
                    .updatedAt(new Date())
                    .discount(0)
                    .quantity(7)
                    .brand("USA")
                    .favorite(false)
                    .user(adminUser)
                    .thumbnail("http://res.cloudinary.com/dxorh7ue1/image/upload/v1708794717/qk4x9tvqxv0fjdtaio6k.jpg")
                    .build();
            productRepository.save(product4);
            Product product3 = Product.builder()
                    .productName("Toilet")
                    .title("Toilet thong minh 2024")
                    .price(110000)
                    .size("693Dx380Wx765D")
                    .color("white")
                    .materials("Su")
                    .description("THE INSIDE Traditional Accent Sofa,Espresso")
                    .createdAt(dateFormat.parse("2024-02-24"))
                    .updatedAt(new Date())
                    .discount(10)
                    .quantity(7)
                    .brand("USA")
                    .favorite(true)
                    .user(adminUser)
                    .thumbnail("http://res.cloudinary.com/dxorh7ue1/image/upload/v1708794717/qk4x9tvqxv0fjdtaio6k.jpg")
                    .build();
            productRepository.save(product3);
            Product product = Product.builder()
                    .productName("DOUBLE SOFA")
                    .title("Sofa phong khach 2024")
                    .price(120000)
                    .size("21H x 55W x 18D")
                    .color("white")
                    .materials("cotton")
                    .description("THE INSIDE Traditional Accent Sofa,Espresso")
                    .createdAt(dateFormat.parse("2024-02-24"))
                    .updatedAt(new Date())
                    .discount(10)
                    .quantity(7)
                    .brand("Japan")
                    .favorite(true)
                    .user(adminUser)
                    .thumbnail("http://res.cloudinary.com/dxorh7ue1/image/upload/v1708794717/qk4x9tvqxv0fjdtaio6k.jpg")
                    .build();
            productRepository.save(product);
            List<Product> listNhaTam = new ArrayList<>();
            listNhaTam.add(product3);
            listNhaTam.add(product4);
            productRepository.saveAll(listNhaTam);
            List<Product> productList = productRepository.findAll();
            Category category = Category.builder()
                    .name("Noi that phong khach")
                    .description("Noi that phong khach")
                    .products(productList)
                    .build();
            categoryRepository.save(category);
            Category category2 = Category.builder()
                    .name("Noi that nha tam")
                    .description("Noi that nha tam 2024")
                    .products(listNhaTam)
                    .build();
            categoryRepository.save(category2);
        };
    }



}
