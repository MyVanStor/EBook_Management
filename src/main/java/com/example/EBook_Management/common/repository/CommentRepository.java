package com.example.EBook_Management.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.example.EBook_Management.common.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{
	List<Comment> findByUserId(@Param("userId") Long userId);
}
