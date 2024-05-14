package com.example.EBook_Management_BE.repositories;

import java.util.List;

import com.example.EBook_Management_BE.entity.Author;
import com.example.EBook_Management_BE.entity.Book;
import com.example.EBook_Management_BE.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.example.EBook_Management_BE.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{
	List<Comment> findByReplyIdAndReplyType(Long replyId,String replyType);
}
