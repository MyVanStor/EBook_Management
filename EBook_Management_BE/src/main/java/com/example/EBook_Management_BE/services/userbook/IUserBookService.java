package com.example.EBook_Management_BE.services.userbook;

import com.example.EBook_Management_BE.entity.UserBook;

public interface IUserBookService {
	UserBook createUserBook(UserBook userBook) throws Exception;
	
	UserBook getUserBookById(Long userBookId) throws Exception;
	
	void deleteUserBook(Long userBookId) throws Exception;
}
