package com.example.EBook_Management_BE.services.userbook;

import com.example.EBook_Management_BE.dtos.UserBookDTO;
import com.example.EBook_Management_BE.entity.UserBook;

public interface IUserBookService {
	UserBook createUserBook(UserBookDTO userBookDTO);
	
	UserBook getUserBookById(Long userBookId);
	
	void deleteUserBook(Long userBookId);
}
