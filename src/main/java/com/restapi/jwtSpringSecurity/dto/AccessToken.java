package com.restapi.jwtSpringSecurity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccessToken {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expire_at")
    private Date expireAt;
}
