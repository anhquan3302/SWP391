package com.example.securityl;

import com.example.securityl.entity.Enum.Role;
import com.example.securityl.request.UserRequest.RegisterRequest;
import com.example.securityl.service.AuthenticationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SecurityLApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityLApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(
            AuthenticationService service
    ){
        return args -> {
            var admin = RegisterRequest.builder()
                    .name("Admin")
                    .address("Viet Nam")
                    .phone("0937534654")
                    .email("admin@gmail.com")
                    .password("admin")
                    .role(Role.ADMIN)
                    .build();
            service.register(admin);
            var staff = RegisterRequest.builder()
                    .name("Manager")
                    .address("Viet Nam")
                    .phone("0937534654")
                    .email("manager@gmail.com")
                    .password("123")
                    .role(Role.STAFF)
                    .build();
            service.register(staff);
        };
    }

}
