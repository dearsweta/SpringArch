package com.spring.internal_working.internal_work.services;


import com.spring.internal_working.internal_work.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.boot.web.server.servlet.context.ServletComponentScan;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {


    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user =userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
        return new User(user.getEmail(),user.getPassword(), Collections.emptyList());
    }
}
