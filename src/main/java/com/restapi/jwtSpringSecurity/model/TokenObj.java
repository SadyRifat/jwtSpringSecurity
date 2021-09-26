package com.restapi.jwtSpringSecurity.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenObj {
    private String username;
    private String purpose;
}
