package com.dev.springsecurityjwt.security.service.impl;

import com.dev.springsecurityjwt.security.domain.entity.MainUser;
import com.dev.springsecurityjwt.security.domain.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserServiceDaoImpl serviceDao;

    public UserDetailsServiceImpl(UserServiceDaoImpl serviceDao) {
        this.serviceDao = serviceDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = serviceDao.getByUsername(username).orElseThrow(() ->
            new IllegalArgumentException("User with name: " + username + "could not be found."));
        return MainUser.build(user);
    }


}
