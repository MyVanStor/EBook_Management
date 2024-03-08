package com.example.EBook_Management_BE.modules.rating.service;

import com.example.EBook_Management_BE.common.entity.Rating;
import com.example.EBook_Management_BE.modules.rating.dto.RatingDTO;

public interface IRatingService {
	Rating createRating(RatingDTO ratingDTO);
	
	Rating getRatingById(Long ratingId);
	
	Rating updateRating(Long ratingId, RatingDTO ratingDTO);

	void deleteRating(Long ratingId);
}
