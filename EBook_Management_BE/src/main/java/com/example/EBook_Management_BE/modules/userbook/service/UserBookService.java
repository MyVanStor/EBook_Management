package com.example.EBook_Management_BE.modules.userbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.EBook_Management_BE.common.entity.Book;
import com.example.EBook_Management_BE.common.entity.User;
import com.example.EBook_Management_BE.common.entity.UserBook;
import com.example.EBook_Management_BE.common.repository.BookRepository;
import com.example.EBook_Management_BE.common.repository.UserBookRepository;
import com.example.EBook_Management_BE.common.repository.UserRepository;
import com.example.EBook_Management_BE.modules.userbook.dto.UserBookDTO;
import com.example.EBook_Management_BE.modules.userbook.mapper.UserBookMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserBookService implements IUserBookService {
	private final UserBookRepository userBookRepository;
	private final UserRepository userRepository;
	private final BookRepository bookRepository;

	@Autowired
	private UserBookMapper userBookMapper;

	@Override
	@Transactional
	public UserBook createUserBook(UserBookDTO userBookDTO) {
		User user = userRepository.findById(userBookDTO.getUserId())
				.orElseThrow(() -> new RuntimeException("User with id = " + userBookDTO.getUserId() + " not found"));

		Book book = bookRepository.findById(userBookDTO.getBookId())
				.orElseThrow(() -> new RuntimeException("Book with id = " + userBookDTO.getBookId() + " not found"));

		UserBook userBook = userBookMapper.mapToUserBookEntity(userBookDTO);
		userBook.setBook(book);
		userBook.setUser(user);

		return userBookRepository.save(userBook);
	}

	@Override
	public UserBook getUserBookById(Long userBookId) {
		return userBookRepository.findById(userBookId)
				.orElseThrow(() -> new RuntimeException(String.format("UserBook with id = %d not found", userBookId)));
	}

	@Override
	@Transactional
	public void deleteUserBook(Long userBookId) {		
		userBookRepository.deleteById(userBookId);
	}

}
