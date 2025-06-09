package com.moneyly.service;

import com.moneyly.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    SupabaseAuthService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = new User();
        Map<String, Object> userMap = userService.getUserByEmail(email);
        if(userMap.containsKey("user")){
            user = (User) userMap.get("user");
        }

        if (user == null) {
            throw new UsernameNotFoundException("User non trovato con email: " + email);
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(email)
                .password(user.getPassword())
                .roles("USER")
                .build();

    }
}
