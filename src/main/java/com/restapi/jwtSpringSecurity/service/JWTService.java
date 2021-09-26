package com.restapi.jwtSpringSecurity.service;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Slf4j
@Service
public class JWTService {
    @Value("${user.manage.site.secretKey}")
    private String jwtSecretKey = "kT2MJt-7NxPM6b-7biRysPw-FcEQHSKf-7Ywae3CU";

    @Value("${user.manage.jwtExpirationHr}")
    private int jwtExpiration;

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        }

        return false;
    }

    public String getSubjectDataFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String generateToken(String subject) {
        Calendar instance = Calendar.getInstance ();
        instance.setTime (new Date ());
        instance.add (Calendar.HOUR, 6);
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date ())
                .setExpiration(instance.getTime ())
                .signWith(SignatureAlgorithm.HS512, jwtSecretKey)
                .compact();
    }
}
