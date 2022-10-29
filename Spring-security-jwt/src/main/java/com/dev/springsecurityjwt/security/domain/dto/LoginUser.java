package com.dev.springsecurityjwt.security.domain.dto;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser {

    @NotBlank
    private String username;
    @NotBlank
    private String password;

}
