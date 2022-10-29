package com.tutorial.crud.security.repository;

import com.tutorial.crud.security.entity.Role;
import com.tutorial.crud.security.enums.NameRole;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByNameRole(NameRole nameRole);


}
