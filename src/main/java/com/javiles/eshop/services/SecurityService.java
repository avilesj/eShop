package com.javiles.eshop.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public interface SecurityService
{
    String findLoggedInUsername();

    void autologin(String username, String password);
}
