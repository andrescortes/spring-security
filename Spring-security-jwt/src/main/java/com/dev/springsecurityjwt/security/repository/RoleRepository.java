package com.dev.springsecurityjwt.security.repository;

import com.dev.springsecurityjwt.security.domain.entity.Role;
import com.dev.springsecurityjwt.security.domain.enums.RoleList;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleName(RoleList roleName);
}
