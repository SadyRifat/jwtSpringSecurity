package com.restapi.jwtSpringSecurity.controller;

import com.restapi.jwtSpringSecurity.security.CurrentUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {
    @GetMapping("/check/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> adminAccess(@CurrentUser User user) {
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
