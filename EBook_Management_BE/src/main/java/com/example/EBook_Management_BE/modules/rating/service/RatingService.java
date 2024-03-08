package com.example.EBook_Management_BE.modules.rating.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.EBook_Management_BE.common.entity.Book;
import com.example.EBook_Management_BE.common.entity.Rating;
import com.example.EBook_Management_BE.common.entity.User;
import com.example.EBook_Management_BE.common.repository.RatingRepository;
import com.example.EBook_Management_BE.modules.book.service.BookService;
import com.example.EBook_Management_BE.modules.rating.dto.RatingDTO;
import com.example.EBook_Management_BE.modules.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RatingService implements IRatingService {
	private final RatingRepository ratingRepository;
	private final UserService userService;
	private final BookService bookService;

	@Override
	@Transactional
	public Rating createRating(RatingDTO ratingDTO) {
		User user = userService.getUserById(ratingDTO.getUserId());

		Book book = bookService.getBookById(ratingDTO.getBookId());

		Rating rating = Rating.builder().score(ratingDTO.getScore()).book(book).user(user).build();

		book.setEvaluate((book.getEvaluate() * book.getNumberOfReview() + ratingDTO.getScore())
				/ (book.getNumberOfReview() + 1));
		book.setNumberOfReview(book.getNumberOfReview() + 1);

		return ratingRepository.save(rating);
	}

	@Override
	public Rating getRatingById(Long ratingId) {
		return ratingRepository.findById(ratingId)
				.orElseThrow(() -> new RuntimeException(String.format("Rating with id = %d not found", ratingId)));
	}

	@Override
	@Transactional
	public Rating updateRating(Long ratingId, RatingDTO ratingDTO) {
		Rating existingRating = getRatingById(ratingId);

		Book book = bookService.getBookById(ratingDTO.getBookId());

		book.setEvaluate(
				(book.getEvaluate() * book.getNumberOfReview() - existingRating.getScore() + ratingDTO.getScore())
						/ book.getNumberOfReview());

		existingRating.setScore(ratingDTO.getScore());
		ratingRepository.save(existingRating);

		return existingRating;

	}

	@Override
	@Transactional
	public void deleteRating(Long ratingId) {
		Rating rating = getRatingById(ratingId);

		Book book = rating.getBook();
		book.setEvaluate(book.getNumberOfReview() == 1 ? 0
				: (book.getEvaluate() * book.getNumberOfReview() - rating.getScore()) / (book.getNumberOfReview() - 1));
		book.setNumberOfReview(book.getNumberOfReview() - 1);
		ratingRepository.deleteById(ratingId);
	}

}
