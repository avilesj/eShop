package com.hercule.eshop.services;

import com.hercule.eshop.models.User;

public interface UserService {
	public void save(User user);
	public User findByUsername(String username);
}
