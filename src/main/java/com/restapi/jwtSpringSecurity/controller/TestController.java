package com.restapi.jwtSpringSecurity.controller;

import com.restapi.jwtSpringSecurity.dto.AccessRequest;
import com.restapi.jwtSpringSecurity.dto.AccessToken;
import com.restapi.jwtSpringSecurity.dto.RegistrationRequest;
import com.restapi.jwtSpringSecurity.entity.UserInfo;
import com.restapi.jwtSpringSecurity.security.CurrentUser;
import com.restapi.jwtSpringSecurity.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Validated
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private UserService userService;

    @PostMapping("/doRegister")
    public ResponseEntity<?> newRegistration(@Valid @RequestBody RegistrationRequest registrationRequest) throws Exception {
        UserInfo userInfo = userService.userRegistration (registrationRequest);
        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }

    @PostMapping("/request/token")
    public ResponseEntity<?> generateAccessToken(@Valid @RequestBody AccessRequest accessRequest) {
        AccessToken accessToken = userService.getAccessToken (accessRequest);
        return new ResponseEntity<>(accessToken, HttpStatus.OK);
    }

    @GetMapping("/check/admin")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> adminAccess(@CurrentUser User coreUser) {
        return ResponseEntity.ok("Admin Accessed");
    }

    @GetMapping("/check/user")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<?> userAccess() {
        return ResponseEntity.ok("User Accessed");
    }

    @GetMapping("/check/moderator")
    @PreAuthorize("hasAnyRole('ROLE_MODERATOR')")
    public ResponseEntity<?> moderatorAccess() {
        return ResponseEntity.ok("Moderator Accessed");
    }
}
