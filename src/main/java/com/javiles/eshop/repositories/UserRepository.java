package com.javiles.eshop.repositories;

import com.javiles.eshop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>
{

    User findByUsername(String username);

    User findOneById(long id);

    List<User> findByUsernameContaining(String name);

}
