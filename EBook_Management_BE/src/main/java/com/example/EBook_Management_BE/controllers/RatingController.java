package com.example.EBook_Management_BE.controllers;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.EBook_Management_BE.components.LocalizationUtils;
import com.example.EBook_Management_BE.dtos.RatingDTO;
import com.example.EBook_Management_BE.entity.Book;
import com.example.EBook_Management_BE.entity.Rating;
import com.example.EBook_Management_BE.entity.User;
import com.example.EBook_Management_BE.constants.Uri;
import com.example.EBook_Management_BE.mappers.RatingMapper;
import com.example.EBook_Management_BE.responses.RatingResponse;
import com.example.EBook_Management_BE.services.book.IBookService;
import com.example.EBook_Management_BE.services.rating.IRatingRedisService;
import com.example.EBook_Management_BE.services.rating.IRatingService;
import com.example.EBook_Management_BE.services.user.IUserService;
import com.example.EBook_Management_BE.utils.MessageKeys;
import com.example.EBook_Management_BE.utils.ResponseObject;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = Uri.RATING)
@Validated
@RequiredArgsConstructor
public class RatingController {
	private final IRatingService ratingService;
	private final IRatingRedisService ratingRedisService;
	private final IUserService userService;
	private final IBookService bookService;
	
	private final LocalizationUtils localizationUtils;
	
	@Autowired
	private RatingMapper ratingMapper;

	@PostMapping()
	@ResponseStatus(code = HttpStatus.CREATED)
//	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<ResponseObject> createRating(@Valid @RequestBody RatingDTO ratingDTO) throws Exception {
		Book book = bookService.getBookById(ratingDTO.getBookId());
		
		User user = userService.getUserById(ratingDTO.getUserId());
		
		Rating rating = Rating.builder()
				.score(ratingDTO.getScore())
				.book(book)
				.user(user)
				.build();

		rating = ratingService.createRating(rating);
		ratingRedisService.saveRatingById(rating.getId(), rating);
		
		RatingResponse ratingResponse = ratingMapper.mapToRatingResponse(rating);
		
		return ResponseEntity.ok(ResponseObject.builder()
				.status(HttpStatus.CREATED)
				.message(localizationUtils.getLocalizedMessage(MessageKeys.RATING_CREATE_SUCCESSFULLY))
				.data(ratingResponse)
				.build());
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<ResponseObject> updateRating(@PathVariable Long id, @Valid @RequestBody RatingDTO ratingDTO) throws Exception {
		Rating rating = ratingService.getRatingById(id);
		
		rating.setScore(ratingDTO.getScore());
		
		rating = ratingService.updateRating(id, rating);
		ratingRedisService.saveRatingById(id, rating);
		
		RatingResponse ratingResponse = ratingMapper.mapToRatingResponse(rating);
		
		return ResponseEntity.ok(ResponseObject.builder()
				.status(HttpStatus.OK)
				.message(localizationUtils.getLocalizedMessage(MessageKeys.RATING_UPDATE_SUCCESSFULLY))
				.data(ratingResponse)
				.build());
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<ResponseObject> deleteRating(@PathVariable Long id) throws Exception {
		Rating existingRating = ratingService.getRatingById(id);
		
		ratingService.deleteRating(existingRating);
		
		return ResponseEntity
				.ok(ResponseObject.builder()
						.status(HttpStatus.OK)
						.message(localizationUtils.getLocalizedMessage(MessageKeys.RATING_DELETE_SUCCESSFULLY))
						.build());
	}
	
	@GetMapping("/all/{bookId}")
	public ResponseEntity<ResponseObject> getAllRatingByBookId(@PathVariable Long bookId) throws Exception {
		Set<Rating> ratings = ratingRedisService.getAllRatingBook(bookId);
		if (ratings == null) {
			Book book = bookService.getBookById(bookId);
		
			ratings = ratingService.getAllRatingByBook(book);
			
			ratingRedisService.saveAllRatingBook(bookId, ratings);
		}
		
		Set<RatingResponse> ratingResponses = new HashSet<RatingResponse>();
		for (Rating rating : ratings) {
			ratingResponses.add(ratingMapper.mapToRatingResponse(rating));
		}
		
		return ResponseEntity.ok(ResponseObject.builder()
				.status(HttpStatus.OK)
				.message(localizationUtils.getLocalizedMessage(MessageKeys.RATING_GET_ALL_RATING_BOOK_SUCCESSFULLY))
				.data(ratingResponses)
				.build());
	}
}
