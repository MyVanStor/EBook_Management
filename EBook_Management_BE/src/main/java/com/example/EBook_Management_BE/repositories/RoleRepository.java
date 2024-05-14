package com.example.EBook_Management_BE.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EBook_Management_BE.entity.Role;


public interface RoleRepository extends JpaRepository<Role, Long>{	
	boolean existsByName(String name);

	Role findByName(String name);
}
