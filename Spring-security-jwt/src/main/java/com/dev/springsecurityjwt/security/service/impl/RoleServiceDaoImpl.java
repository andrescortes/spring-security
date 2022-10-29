package com.dev.springsecurityjwt.security.service.impl;

import com.dev.springsecurityjwt.security.domain.entity.Role;
import com.dev.springsecurityjwt.security.domain.enums.RoleList;
import com.dev.springsecurityjwt.security.repository.RoleRepository;
import com.dev.springsecurityjwt.security.service.contract.RoleServiceDao;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleServiceDaoImpl implements RoleServiceDao {

    private final RoleRepository roleRepository;

    public RoleServiceDaoImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Role> getByRoleName(RoleList roleName) {
        return roleRepository.findByRoleName(roleName);
    }
}
