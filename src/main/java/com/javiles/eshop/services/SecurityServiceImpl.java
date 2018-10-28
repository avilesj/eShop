package com.javiles.eshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class SecurityServiceImpl implements SecurityService
{

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public String findLoggedInUsername()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication.getName();
    }

    @Override
    public void autologin(String username, String password)
    {
        /*
         * Get UserDetails object using Spring's User object, converted from our own User object
         * using UserDetailsService. UserDetails is the User object required to get the authentication.
         */

        UserDetails userDetails = userDetailsService.loadUserByUsername(username.toLowerCase());

        /*
         * To add an authentication to the SecurityContext, in this case you need UsernamePasswordAuthenticationToken,
         * which is a Authentication object.
         * Give this object userDetails, the input password and the userDetails's authorities (in this case) and It's
         * going to be ready to try and be authenticated.
         */

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        /*
         * Attempt user authentication using usernamePasswordToken.If successful, our token will be authenticated (boolean)
         */
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        if (usernamePasswordAuthenticationToken.isAuthenticated())
        {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
    }

}
