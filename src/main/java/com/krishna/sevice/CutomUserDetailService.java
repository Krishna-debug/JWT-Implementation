package com.krishna.sevice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.krishna.domain.Users;
import com.krishna.global.exception.ResourceNotFoundException;
import com.krishna.repo.UserRepo;

@Service
public class CutomUserDetailService implements UserDetailsService {

	@Autowired
	UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws ResourceNotFoundException {

		// loading user from database by user
		Users user = userRepo.findByEmail(username);

		if (user != null)
			return user;
		else {
			throw new ResourceNotFoundException("User not found !");
		}
	}

}
