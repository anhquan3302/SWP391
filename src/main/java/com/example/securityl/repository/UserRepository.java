package com.example.securityl.repository;

import com.example.securityl.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    Optional<UserDTO> findUserByEmail(String userEmail);

    User  findUserIdByEmail(String email);

    User findUsersByUserId(Integer id);

    boolean existsByPhone(String phone);

    User findUsersByEmail(String email);


    @Query("SELECT u FROM User u WHERE " +
            "(:searchValue IS NULL OR LOWER(u.name) LIKE %:searchValue% OR LOWER(u.email) LIKE %:searchValue%) AND " +
            "(:address IS NULL OR LOWER(u.address) LIKE %:address%) AND " +
            "(:userId IS NULL OR u.userId = :userId)")
    List<User> findUsersByFilter(String searchValue, String address, Integer userId);


}
