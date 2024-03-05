package com.example.EBook_Management_BE.common.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EBook_Management_BE.common.entity.Role;
import com.example.EBook_Management_BE.common.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	boolean existsByPhoneNumber(String phoneNumber);
	
	Optional<User> findByPhoneNumber(String phoneNumber);
	
	List<User> findByRole(Role role);

}
