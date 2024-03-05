package com.example.EBook_Management_BE.modules.userbook.service;

import com.example.EBook_Management_BE.common.entity.UserBook;
import com.example.EBook_Management_BE.modules.userbook.dto.UserBookDTO;

public interface IUserBookService {
	UserBook createUserBook(UserBookDTO userBookDTO);
	
	UserBook getUserBookById(Long userBookId);
	
	void deleteUserBook(Long userBookId);
}
