package com.example.EBook_Management_BE.services.userbook;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.EBook_Management_BE.components.LocalizationUtils;
import com.example.EBook_Management_BE.entity.UserBook;
import com.example.EBook_Management_BE.exceptions.DataNotFoundException;
import com.example.EBook_Management_BE.exceptions.DuplicateException;
import com.example.EBook_Management_BE.repositories.UserBookRepository;
import com.example.EBook_Management_BE.utils.MessageExceptionKeys;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserBookService implements IUserBookService {
	private final UserBookRepository userBookRepository;
	private final IUserBookRedisService userBookRedisService;
	
	private final LocalizationUtils localizationUtils;

	@Override
	@Transactional
	public UserBook createUserBook(UserBook userBook) throws Exception {
		if (userBookRepository.existsByUserAndBook(userBook.getUser(), userBook.getBook())) {
			throw new DuplicateException(localizationUtils.getLocalizedMessage(MessageExceptionKeys.USER_BOOK_DUPLICATE_USER_AND_BOOK));
		}
		
		userBookRepository.save(userBook);
		userBookRedisService.saveUserBookById(userBook.getId(), userBook);
		return userBook;
	}

	@Override
	public UserBook getUserBookById(Long userBookId) throws Exception {
		UserBook userBook = userBookRedisService.getUserBookById(userBookId);
		if (userBook == null) {
			userBook = userBookRepository.findById(userBookId)
					.orElseThrow(() -> new DataNotFoundException(localizationUtils.getLocalizedMessage(MessageExceptionKeys.USER_BOOK_NOT_FOUND)));
		}
		userBookRedisService.saveUserBookById(userBook.getId(), userBook);
		
		return userBook;
	}

	@Override
	@Transactional
	public void deleteUserBook(Long userBookId) throws Exception {	
		userBookRepository.deleteById(userBookId);
	}

}
