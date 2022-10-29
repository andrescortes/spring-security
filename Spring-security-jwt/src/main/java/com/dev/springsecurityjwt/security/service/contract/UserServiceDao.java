package com.dev.springsecurityjwt.security.service.contract;

import com.dev.springsecurityjwt.security.domain.entity.User;
import java.util.Optional;

public interface UserServiceDao {
    Optional<User> getByUsername(String username);
    boolean existByUsername(String username);

    User saveUser(User user);
}
