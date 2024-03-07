package com.example.EBook_Management_BE.common.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EBook_Management_BE.common.entity.Follow;
import com.example.EBook_Management_BE.common.entity.User;


public interface FollowRepository extends JpaRepository<Follow, Long>{
	Set<Follow> findByUser(User user);
	
	Set<Follow> findByFollowing(Long following);
}
