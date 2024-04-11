package com.example.EBook_Management_BE.services.rating;

import com.example.EBook_Management_BE.dtos.RatingDTO;
import com.example.EBook_Management_BE.entity.Rating;

public interface IRatingService {
	Rating createRating(RatingDTO ratingDTO);
	
	Rating getRatingById(Long ratingId);
	
	Rating updateRating(Long ratingId, RatingDTO ratingDTO);

	void deleteRating(Long ratingId);
	
	int getAllRatingByBookId(Long bookId);
}
