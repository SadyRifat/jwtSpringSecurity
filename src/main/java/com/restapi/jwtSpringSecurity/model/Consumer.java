package com.restapi.jwtSpringSecurity.model;

import com.restapi.jwtSpringSecurity.enums.ERole;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class Consumer {
    private String consumerID;
    private Set<ERole> eRoles;
}
