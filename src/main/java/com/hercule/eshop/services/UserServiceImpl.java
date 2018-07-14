package com.hercule.eshop.services;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.hercule.eshop.models.User;
import com.hercule.eshop.repositories.RoleRepository;
import com.hercule.eshop.repositories.UserRepository;


public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder; 
	
	@Override
	public void save(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRoles(new HashSet<>(roleRepository.findAll()));
		user.setUsername(user.getUsername().toUpperCase());
		userRepository.save(user);
}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

}
