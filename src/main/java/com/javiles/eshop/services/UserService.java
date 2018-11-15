package com.javiles.eshop.services;

import com.javiles.eshop.models.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public interface UserService
{
    void save(User user);

    User findByUsername(String username);

    User findByUserId(long id);

    void deleteUserById(long id);

    List<User> searchUserByUsername(String name);

    void updateUserPasswordAndRoles(User user);

    void updateUserPersonalInformation(User user);

    void updateUserPassword(User user);
}
