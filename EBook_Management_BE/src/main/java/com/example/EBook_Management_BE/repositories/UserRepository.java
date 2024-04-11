package com.example.EBook_Management_BE.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.EBook_Management_BE.entity.Role;
import com.example.EBook_Management_BE.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	boolean existsByPhoneNumber(String phoneNumber);
	
	Optional<User> findByPhoneNumber(String phoneNumber);
	
	List<User> findByRole(Role role);
	
	@Query("SELECT o FROM User o WHERE o.isActive = 1 AND (:keyword IS NULL OR :keyword = '' OR " +
            "o.fullname LIKE %:keyword% " +
            "OR o.phoneNumber LIKE %:keyword%) " +
            "AND LOWER(o.role.name) = 'user'")
    Page<User> findAll(@Param("keyword") String keyword, Pageable pageable);
}
