package com.example.EBook_Management_BE.services.userbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.EBook_Management_BE.dtos.UserBookDTO;
import com.example.EBook_Management_BE.entity.Book;
import com.example.EBook_Management_BE.entity.User;
import com.example.EBook_Management_BE.entity.UserBook;
import com.example.EBook_Management_BE.mappers.UserBookMapper;
import com.example.EBook_Management_BE.repositories.BookRepository;
import com.example.EBook_Management_BE.repositories.UserBookRepository;
import com.example.EBook_Management_BE.repositories.UserRepository;

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
