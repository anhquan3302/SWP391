package com.example.securityl;

import com.example.securityl.entity.Role;
import com.example.securityl.request.RegisterRequest;
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


            var manager = RegisterRequest.builder()
                    .name("Manager")
                    .address("Viet Nam")
                    .phone("0937534654")
                    .email("manager@gmail.com")
                    .password("123")
                    .role(Role.MANAGER)
                    .build();
            System.out.println("Manager token :"  + service.register(manager).getToken());
            System.out.println("Admin token :"  + service.register(admin).getToken());
        };
    }

}
