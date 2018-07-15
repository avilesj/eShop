package com.hercule.eshop.services;

import com.hercule.eshop.models.User;

public interface UserService
{
    void save(User user);

    User findByUsername(String username);
}
