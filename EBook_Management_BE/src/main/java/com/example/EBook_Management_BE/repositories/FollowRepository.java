package com.example.EBook_Management_BE.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EBook_Management_BE.entity.Follow;
import com.example.EBook_Management_BE.entity.User;


public interface FollowRepository extends JpaRepository<Follow, Long>{
	Set<Follow> findByUser(User user);
	
	Set<Follow> findByFollowing(Long following);

	Follow findByFollowingAndUser(Long following, User user);
}
