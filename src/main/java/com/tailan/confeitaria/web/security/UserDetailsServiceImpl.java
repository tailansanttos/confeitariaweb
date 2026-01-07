package com.tailan.confeitaria.web.security;

import com.tailan.confeitaria.web.domain.User;
import com.tailan.confeitaria.web.infra.exception.ResourceNotFoundException;
import com.tailan.confeitaria.web.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByEmail(username);
    }
}
