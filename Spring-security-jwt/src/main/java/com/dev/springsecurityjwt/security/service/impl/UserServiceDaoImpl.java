package com.dev.springsecurityjwt.security.service.impl;

import com.dev.springsecurityjwt.security.domain.entity.User;
import com.dev.springsecurityjwt.security.repository.UserRepository;
import com.dev.springsecurityjwt.security.service.contract.UserServiceDao;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceDaoImpl implements UserServiceDao {

    private final UserRepository userRepository;

    public UserServiceDaoImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Transactional
    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
