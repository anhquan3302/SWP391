package com.example.securityl.repository;

import com.example.securityl.entity.Enum.Role;
import com.example.securityl.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    Optional<UserDTO> findUserByEmail(String userEmail);

    User  findUserIdByEmail(String email);

    Optional<User> findUsersByUserId(Integer id);
}
