package com.krishna.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.krishna.domain.Users;

@Repository
public interface UserRepo extends JpaRepository<Users, Long>{

	Users findByEmail(String username);

}
