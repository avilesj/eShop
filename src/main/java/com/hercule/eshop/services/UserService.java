package com.hercule.eshop.services;

import com.hercule.eshop.models.User;

import java.util.List;

public interface UserService
{
    void save(User user);

    User findByUsername(String username);

    List<User> searchUserByUsername(String name);
}
