package com.javiles.eshop.services;

import com.javiles.eshop.models.Role;
import com.javiles.eshop.models.User;
import com.javiles.eshop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

public class UserDetailsServiceImpl implements UserDetailsService
{

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        //Gets our User object from the database
        User user = userRepository.findByUsername(username.toLowerCase());
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        //Iterates over our User's roles to transform them into Spring Security's granted authorities.
        for (Role role : user.getRoles())
        {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        //Returns spring security User object instead of our own.
        return new org.springframework.security.core.userdetails.User(user.getUsername().toLowerCase(), user.getPassword(), grantedAuthorities);
    }

}
