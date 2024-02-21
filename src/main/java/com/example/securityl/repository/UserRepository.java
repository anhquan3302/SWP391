package com.example.securityl.repository;

import com.example.securityl.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    @Query("SELECT u.name FROM User u WHERE u.email = :email")
    Optional<String> findUsernameByEmail(@Param("email") String email);

    Optional<UserDTO> findUserByEmail(String userEmail);

    User  findUserIdByEmail(String email);

    User findUsersByUserId(Integer id);

    User findUsersByEmail(String email);
}
