package com.example.securityl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.securityl.model.Category;
import com.example.securityl.model.CategoryProduct;
import com.example.securityl.model.Enum.Role;
import com.example.securityl.model.Products;
import com.example.securityl.model.User;
import com.example.securityl.repository.CategoryProductRepository;
import com.example.securityl.repository.CategoryRepository;
import com.example.securityl.repository.ProductRepository;
import com.example.securityl.request.UserRequest.RegisterRequest;
import com.example.securityl.service.AuthenticationService;
import com.example.securityl.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.text.SimpleDateFormat;
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
            ProductRepository productRepository,
            CategoryRepository categoryRepository,
            CategoryProductRepository categoryProductRepository,
            UserService userService
    ) {
        return args -> {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            RegisterRequest adminRequest = RegisterRequest.builder()
                    .name("Admin")
                    .address("Viet Nam")
                    .phone("0937534654")
                    .email("admin@gmail.com")
                    .password("admin")
                    .role(Role.ADMIN)
                    .build();
            service.register(adminRequest);
            User adminUser = userService.findByEmailForMail(adminRequest.getEmail());


            Products product2 = Products.builder()
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
                    .user(adminUser)
                    .thumbnail("https://bizweb.dktcdn.net/100/467/207/products/screenshot-2023-09-22-121711.jpg?v=1696500727497")
                    .build();
            productRepository.save(product2);

            Products product = Products.builder()
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
                    .user(adminUser)
                    .thumbnail("http://res.cloudinary.com/dxorh7ue1/image/upload/v1708794717/qk4x9tvqxv0fjdtaio6k.jpg")
                    .build();
            productRepository.save(product);
            List<Products> productList = productRepository.findAll();


        };
    }



}
