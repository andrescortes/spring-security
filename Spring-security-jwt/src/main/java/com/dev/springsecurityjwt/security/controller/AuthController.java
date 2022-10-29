package com.dev.springsecurityjwt.security.controller;

import com.dev.springsecurityjwt.domain.entity.Message;
import com.dev.springsecurityjwt.security.domain.dto.JwtDto;
import com.dev.springsecurityjwt.security.domain.dto.LoginUser;
import com.dev.springsecurityjwt.security.domain.dto.NewUser;
import com.dev.springsecurityjwt.security.domain.entity.Role;
import com.dev.springsecurityjwt.security.domain.entity.User;
import com.dev.springsecurityjwt.security.domain.enums.RoleList;
import com.dev.springsecurityjwt.security.jwt.JwtProvider;
import com.dev.springsecurityjwt.security.service.impl.RoleServiceDaoImpl;
import com.dev.springsecurityjwt.security.service.impl.UserServiceDaoImpl;
import java.util.HashSet;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final UserServiceDaoImpl userServiceDaoImpl;
    private final RoleServiceDaoImpl roleServiceDaoImpl;
    private final JwtProvider jwtProvider;

    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder,
        PasswordEncoder passwordEncoder, UserServiceDaoImpl userServiceDaoImpl,
        RoleServiceDaoImpl roleServiceDaoImpl, JwtProvider jwtProvider) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.passwordEncoder = passwordEncoder;
        this.userServiceDaoImpl = userServiceDaoImpl;
        this.roleServiceDaoImpl = roleServiceDaoImpl;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody final LoginUser loginUser,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(new Message("Credentials invalid, verify fields."),
                HttpStatus.BAD_REQUEST);
        }
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser.getUsername(),
                    loginUser.getPassword());
            Authentication authentication = authenticationManagerBuilder.getObject()
                .authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtProvider.generateToken(authentication);
            JwtDto jwtDto = new JwtDto(jwt);
            return new ResponseEntity<>(jwtDto, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error = " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(new Message("Credentials invalid."),
                HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody final NewUser newUser,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(new Message("Checks fields and try again."),
                HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setUsername(newUser.getUsername());
        user.setEmail(newUser.getEmail());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleServiceDaoImpl.getByRoleName(RoleList.ROLE_USER).get());
        if (newUser.getRoles().contains("admin")) {
            roles.add(roleServiceDaoImpl.getByRoleName(RoleList.ROLE_ADMIN).get());
        }
        user.setRoles(roles);
        userServiceDaoImpl.saveUser(user);
        return new ResponseEntity<>(new Message("User saved successfully! Log in"), HttpStatus.OK);
    }
}
