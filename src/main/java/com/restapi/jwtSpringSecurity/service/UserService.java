package com.restapi.jwtSpringSecurity.service;

import com.restapi.jwtSpringSecurity.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserCredentialRepository userCredentialRepository;

}
