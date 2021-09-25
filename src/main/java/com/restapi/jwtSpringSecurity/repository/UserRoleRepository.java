package com.restapi.jwtSpringSecurity.repository;

import com.restapi.jwtSpringSecurity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<User, String>{
}
