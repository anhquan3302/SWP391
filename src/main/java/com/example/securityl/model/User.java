package com.example.securityl.model;

import com.example.securityl.model.Enum.Role;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private boolean status;

    @Enumerated(EnumType.STRING)
    private Role role;

//    @OneToMany(mappedBy = "user")
//    @JsonManagedReference
//    private List<Token> tokens;
//
//    @OneToMany(mappedBy = "user")
//    @JsonManagedReference
//    @ToString.Exclude
//    private List<Products> products;

//    @OneToMany(mappedBy = "user")
//    @JsonManagedReference
//    private List<Orders> orders;
//
//    @OneToMany(mappedBy = "user")
//    @JsonManagedReference
//    private List<BookingDesign> bookings;
//
//    @OneToMany(mappedBy = "user")
//    @JsonManagedReference
//    private List<Blog> blogs;
//
//    @OneToMany(mappedBy = "user")
//    @JsonManagedReference
//    private List<Feedback> feedbacks;
//
//    @OneToMany(mappedBy = "user")
//    @JsonManagedReference
//    private List<OrderDesign> payments;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }



}
