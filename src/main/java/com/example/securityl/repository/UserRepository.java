package com.example.securityl.repository;

import com.example.securityl.model.User;
import com.example.securityl.request.UserRequest.SearchRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    Optional<UserDTO> findUserByEmail(String userEmail);

    User  findUserIdByEmail(String email);

    Optional<User> findUsersByUserId(Integer id);

    @Query("Select User from User u where (:#{#request.userId} is null or " +
            "u.userId = :#{#request.userId}) and " +
            "(:#{#request.name} is null or u.name LIKE :#{#request.name}) and " +
            "(:#{#request.email} is null or u.email LIKE :#{#request.email})")
    List<User> searchUsers(@Param("request") SearchRequest request);
}
