package com.example.EBook_Management_BE.services.rating;

import java.util.Set;

import com.example.EBook_Management_BE.entity.Rating;

public interface IRatingRedisService {
	void clearById(Long id);

	Rating getRatingById(Long ratingId) throws Exception;

	void saveRatingById(Long ratingId, Rating rating) throws Exception;
	
	Set<Rating> getAllRatingBook(Long bookId) throws Exception;
	
	void saveAllRatingBook(Long bookId, Set<Rating> ratings) throws Exception;
}
