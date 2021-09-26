package com.restapi.jwtSpringSecurity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class RegistrationRequest {
    @JsonProperty("first_name")
    @NotBlank(message = "First name is required.")
    @Size(max = 50, message = "Field length should not greater than 50")
    private String firstName;

    @JsonProperty("last_name")
    @NotBlank(message = "Last name is required.")
    @Size(max = 50, message = "Field length should not greater than 50")
    private String lastName;

    @Email
    @JsonProperty("email")
    @NotBlank(message = "Email is required.")
    @Size(max = 50, message = "Field length should not greater than 50")
    private String email;

    @JsonProperty("password")
    @NotBlank(message = "Password is required.")
    @Size(max = 20, message = "Field length should not greater than 20")
    private String password;

    @JsonProperty("confirm_password")
    @NotBlank(message = "Confirm Password is required.")
    @Size(max = 20, message = "Field length should not greater than 20")
    private String confirmPassword;
}
