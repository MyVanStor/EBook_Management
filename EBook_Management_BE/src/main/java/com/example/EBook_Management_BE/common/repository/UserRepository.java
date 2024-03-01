package com.example.EBook_Management_BE.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EBook_Management_BE.common.entity.Role;
import com.example.EBook_Management_BE.common.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	List<User> findByRole(Role role);

}
