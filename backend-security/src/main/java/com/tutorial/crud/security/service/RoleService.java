package com.tutorial.crud.security.service;

import com.tutorial.crud.security.entity.Role;
import com.tutorial.crud.security.enums.NameRole;
import com.tutorial.crud.security.repository.RoleRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Optional<Role> findByNameRole(NameRole nameRole) {
        return roleRepository.findByNameRole(nameRole);
    }
}
