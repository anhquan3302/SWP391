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


import static com.example.securityl.entity.Enum.Permission.*;
import static com.example.securityl.entity.Enum.Role.ADMIN;
import static com.example.securityl.entity.Enum.Role.STAFF;
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
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers("/api/v1/managemnet/**").hasAnyRole(ADMIN.name(), STAFF.name())
                        .requestMatchers(GET, "/api/v1/managemnet/**").hasAnyAuthority(ADMIN_VIEW.name(), STAFF_VIEW.name())
                        .requestMatchers(POST, "/api/v1/managemnet/**").hasAnyAuthority(ADMIN_CREATE.name(), STAFF_CREATE.name())
                        .requestMatchers(PUT, "/api/v1/managemnet/**").hasAnyAuthority(ADMIN_UPDATE.name(), STAFF_UPDATE.name())
                        .requestMatchers(DELETE, "/api/v1/managemnet/**").hasAnyAuthority(ADMIN_DELETE.name(), STAFF_DELETE.name())

                        .anyRequest().authenticated())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(o -> o
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