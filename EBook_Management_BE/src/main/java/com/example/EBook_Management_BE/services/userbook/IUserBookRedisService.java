package com.example.EBook_Management_BE.services.userbook;

import com.example.EBook_Management_BE.entity.UserBook;

public interface IUserBookRedisService {
	void clearById(Long id);

	UserBook getUserBookById(Long userBookId) throws Exception;

	void saveUserBookById(Long userBookId, UserBook author) throws Exception;
}
