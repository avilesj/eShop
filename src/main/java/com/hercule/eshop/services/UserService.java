package com.hercule.eshop.services;

import com.hercule.eshop.models.User;

import java.util.List;

public interface UserService
{
    void save(User user);

    User findByUsername(String username);

    User findByUserId(long id);

    void deleteUser(User user);

    List<User> searchUserByUsername(String name);

    void updateUser(User user);
}
