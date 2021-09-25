package com.restapi.jwtSpringSecurity.repository;

import com.restapi.jwtSpringSecurity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialRepository extends JpaRepository<User, String> {
    @Query("SELECT user FROM User user WHERE user.username=:username")
    Optional<User> findByUsername(String username);

    @Query("SELECT user FROM User user WHERE user.email=:email")
    Optional<User> findByUserEmail(String email);
}
