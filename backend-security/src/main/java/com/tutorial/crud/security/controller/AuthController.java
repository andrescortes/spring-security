package com.tutorial.crud.security.controller;

import com.tutorial.crud.dto.Message;
import com.tutorial.crud.security.dto.JwtDto;
import com.tutorial.crud.security.dto.LoginUser;
import com.tutorial.crud.security.dto.NewUser;
import com.tutorial.crud.security.entity.Role;
import com.tutorial.crud.security.entity.User;
import com.tutorial.crud.security.enums.NameRole;
import com.tutorial.crud.security.jwt.JwtProvider;
import com.tutorial.crud.security.service.RoleService;
import com.tutorial.crud.security.service.UserService;
import java.util.HashSet;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final RoleService roleService;

    private final JwtProvider jwtProvider;

    public AuthController(PasswordEncoder passwordEncoder,
        AuthenticationManager authenticationManager, UserService userService,
        RoleService roleService, JwtProvider jwtProvider) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.roleService = roleService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/save")
    public ResponseEntity<?> addUser(
        @Valid @RequestBody NewUser newUser,
        BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new Message("Fields are not valid"));
        }

        if (userService.existsByUsername(newUser.getUsername())) {
            return ResponseEntity.badRequest().body(new Message("Username is already in use"));
        }

        if (userService.existsByEmail(newUser.getEmail())) {
            return ResponseEntity.badRequest().body(new Message("Email is already in use"));
        }

        User user = new User(newUser.getName(), newUser.getUsername(),
            passwordEncoder.encode(newUser.getPassword()), newUser.getEmail());

        Set<Role> roles = new HashSet<>();
        roles.add(roleService.findByNameRole(NameRole.ROLE_USER).get());

        if (newUser.getRoles() != null) {
            if (newUser.getRoles().contains("admin")) {
                roles.add(roleService.findByNameRole(NameRole.ROLE_ADMIN).get());
            }
        }

        user.setRoles(roles);
        userService.saveUser(user);
        return new ResponseEntity(new Message("User created"),
            HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginUser loginUser,
        BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new Message("Fields are not valid"));
        }

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginUser.getUsername(),
                loginUser.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
        return ResponseEntity.ok(jwtDto);
    }

}
