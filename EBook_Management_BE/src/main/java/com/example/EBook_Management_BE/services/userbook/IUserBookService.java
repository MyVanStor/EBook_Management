package com.example.EBook_Management_BE.services.userbook;

import com.example.EBook_Management_BE.entity.UserBook;
import com.example.EBook_Management_BE.exceptions.DataNotFoundException;

public interface IUserBookService {
	UserBook createUserBook(UserBook userBook);
	
	UserBook getUserBookById(Long userBookId) throws DataNotFoundException;
	
	void deleteUserBook(Long userBookId) throws Exception;
}
