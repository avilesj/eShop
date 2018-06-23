package com.hercule.eshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hercule.eshop.models.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByUsername(String username);
	
}
