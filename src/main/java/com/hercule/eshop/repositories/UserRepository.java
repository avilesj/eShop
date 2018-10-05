package com.hercule.eshop.repositories;

import com.hercule.eshop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>
{

    User findByUsername(String username);

    List<User> findByUsernameContaining(String name);

}
