package com.restapi.jwtSpringSecurity.service;

import com.google.gson.Gson;
import com.restapi.jwtSpringSecurity.dto.AccessRequest;
import com.restapi.jwtSpringSecurity.dto.AccessToken;
import com.restapi.jwtSpringSecurity.dto.RegistrationRequest;
import com.restapi.jwtSpringSecurity.entity.Role;
import com.restapi.jwtSpringSecurity.entity.UserInfo;
import com.restapi.jwtSpringSecurity.enums.ERole;
import com.restapi.jwtSpringSecurity.model.Consumer;
import com.restapi.jwtSpringSecurity.repository.UserInfoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private JWTService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    UserInfoRepository userInfoRepository;

    public AccessToken getAccessToken(AccessRequest accessRequest) {
        UserInfo userInfo = userInfoRepository.findByUserEmail (accessRequest.getUserEmail ()).orElse (null);
        AccessToken accessToken = new AccessToken ();

        if (userInfo != null && passwordEncoder.matches (accessRequest.getPassword (), userInfo.getPassword ())) {
            Set<ERole> roles = userInfo.getRoles ().stream ().map (Role::getName).collect (Collectors.toSet ());
            Consumer consumer = new Consumer (userInfo.getEmail (), roles);
            String token = jwtService.generateToken (new Gson ().toJson (consumer));

            accessToken.setAccessToken (token);
            accessToken.setExpireAt (jwtService.getTokenExpiredTime ());
        } else {
            throw new UsernameNotFoundException ("Email or Password mismatched");
        }
        return accessToken;
    }

    public UserInfo userRegistration(RegistrationRequest registrationRequest) throws Exception {
        UserInfo userInfo = userInfoRepository.findByUserEmail (registrationRequest.getEmail ()).orElse (null);
        if (userInfo == null) {
            UserInfo newUserInfo = new UserInfo ();
            BeanUtils.copyProperties (registrationRequest, newUserInfo);
            newUserInfo.setId (UUID.randomUUID ().toString ());
            newUserInfo.setUsername (registrationRequest.getEmail ());
            newUserInfo.setPassword (passwordEncoder.encode (registrationRequest.getPassword ()));
            userInfoRepository.save (newUserInfo);
            return newUserInfo;
        } else {
            throw new Exception ("User already exists");
        }
    }
}
