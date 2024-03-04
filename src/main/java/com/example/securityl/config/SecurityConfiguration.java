package com.example.securityl.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;


import static com.example.securityl.model.Enum.Permission.*;
import static com.example.securityl.model.Enum.Role.*;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final AuthenticationProvider authenticationProvider;

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final LogoutHandler logoutHandler;
    private final CustomOAuth2AuthenticationSuccessHandler customOAuth2AuthenticationSuccessHandler;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
//                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configure(http))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authentication -> authentication
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers("/api/v1/shoppingCart").permitAll()
                        .requestMatchers("/api/v1/home/**").permitAll()
                        .requestMatchers("/api/v1/feedBack/**").permitAll()
                        .requestMatchers("/api/v1/vnpay/**").permitAll()
                        .requestMatchers("/api/v1/feedBack/**").hasAnyRole(staff.name())

                        .requestMatchers("/api/v1/order/**").hasAnyRole(deliveryStaff.name())

                        .requestMatchers("/api/v1/blog/**").hasAnyRole(admin.name())

                        .requestMatchers("/api/v1/category/**").permitAll()
                        .requestMatchers(GET, "/api/v1/category/**").permitAll()
                        .requestMatchers(POST, "/api/v1/category/**").hasAnyAuthority(ADMIN_CREATE.name())
                        .requestMatchers(PUT, "/api/v1/category/**").hasAnyAuthority(ADMIN_UPDATE.name())
                        .requestMatchers(DELETE, "/api/v1/category/**").hasAnyAuthority(ADMIN_DELETE.name())

                        .requestMatchers("/api/v1/product/**").permitAll()
                        .requestMatchers(GET, "/api/v1/product/**").permitAll()
                        .requestMatchers(POST, "/api/v1/product/**").hasAnyAuthority(ADMIN_CREATE.name())
                        .requestMatchers(PUT, "/api/v1/product/**").hasAnyAuthority(ADMIN_UPDATE.name())
                        .requestMatchers(DELETE, "/api/v1/product/**").hasAnyAuthority(ADMIN_DELETE.name())


                        .requestMatchers( "/api/v1/coupon/**").permitAll()
                        .requestMatchers(GET, "/api/v1/coupon/**").permitAll()
                        .requestMatchers(POST, "/api/v1/coupon/**").hasAnyAuthority(ADMIN_CREATE.name())
                        .requestMatchers(PUT, "/api/v1/coupon/**").hasAnyAuthority(ADMIN_UPDATE.name())
                        .requestMatchers(DELETE, "/api/v1/coupon/**").hasAnyAuthority(ADMIN_DELETE.name())


                        .anyRequest().authenticated())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(o -> o
                        .loginPage("/login")
                        .successHandler(customOAuth2AuthenticationSuccessHandler)
                        .failureHandler((request, response, exception) -> {
                            request.getSession().setAttribute("error.message", exception.getMessage());
                        })
                )
                .logout(logoutRequest -> logoutRequest
                        .logoutUrl("/authentication/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler((request, response, authentication) ->
                                SecurityContextHolder.clearContext())
                );
        return http.build();

    }


}