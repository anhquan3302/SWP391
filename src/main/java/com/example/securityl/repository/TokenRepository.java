package com.example.securityl.repository;

import com.example.securityl.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query("""
                    SELECT t FROM Token t INNER JOIN User u ON t.user.userId = u.userId
                    WHERE u.userId = :userId AND (t.expired = FALSE OR t.revoked = FALSE)
            """)
    List<Token> findAllVaildTokenByUser(Integer userId);

    Optional<Token> findByToken(String token);
}
