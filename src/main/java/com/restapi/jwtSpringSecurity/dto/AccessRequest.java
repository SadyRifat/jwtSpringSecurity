package com.restapi.jwtSpringSecurity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class AccessRequest {
	@Email
	@NotBlank(message = "Email can not be blank")
	@JsonProperty("user_email")
	private String userEmail;

	@NotBlank(message = "Passsword can not be blank")
	@JsonProperty("password")
	private String password;
}
