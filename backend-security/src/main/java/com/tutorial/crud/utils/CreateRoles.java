package com.tutorial.crud.utils;

import com.tutorial.crud.security.entity.Role;
import com.tutorial.crud.security.enums.NameRole;
import com.tutorial.crud.security.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//@Component
public class CreateRoles implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public CreateRoles(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Role roleAdmin = new Role(NameRole.ROLE_ADMIN);
        Role roleUser = new Role(NameRole.ROLE_USER);
        roleRepository.save(roleAdmin);
        roleRepository.save(roleUser);
    }
}
