package com.dev.springsecurityjwt.security.service.contract;

import com.dev.springsecurityjwt.security.domain.entity.Role;
import com.dev.springsecurityjwt.security.domain.enums.RoleList;
import java.util.Optional;

public interface RoleServiceDao {
    Optional<Role> getByRoleName(RoleList roleName);
}
