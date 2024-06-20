package com.example.EBook_Management_BE.services.rating;

import java.util.List;
import java.util.Set;

import com.example.EBook_Management_BE.exceptions.DuplicateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.EBook_Management_BE.components.LocalizationUtils;
import com.example.EBook_Management_BE.entity.Book;
import com.example.EBook_Management_BE.entity.Rating;
import com.example.EBook_Management_BE.exceptions.DataNotFoundException;
import com.example.EBook_Management_BE.repositories.RatingRepository;
import com.example.EBook_Management_BE.utils.MessageExceptionKeys;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RatingService implements IRatingService {
	private final RatingRepository ratingRepository;
	private final IRatingRedisService ratingRedisService;
	
	private final LocalizationUtils localizationUtils;

	@Override
	@Transactional
	public Rating createRating(Rating rating) throws DuplicateException {
		if (ratingRepository.existsByUserAndBook(rating.getUser(), rating.getBook())) {
			throw new DuplicateException("Đã ồn tại");
		}
		return ratingRepository.save(rating);
	}

	@Override
	public Rating getRatingById(Long ratingId) throws Exception {
		Rating rating = ratingRedisService.getRatingById(ratingId);
		if (rating == null) {
			rating = ratingRepository.findById(ratingId)
					.orElseThrow(() -> new DataNotFoundException(localizationUtils.getLocalizedMessage(MessageExceptionKeys.RATING_NOT_FOUND)));
			
			ratingRedisService.saveRatingById(ratingId, rating);
		}
		return rating;
	}

	@Override
	@Transactional
	public Rating updateRating(Long ratingId, Rating rating) {
		return ratingRepository.save(rating);
	}

	@Override
	@Transactional
	public void deleteRating(Rating rating) {
		ratingRepository.delete(rating);
	}

	@Override
	public Set<Rating> getAllRatingByBook(Book book) throws DataNotFoundException {
		Set<Rating> ratings = ratingRepository.findByBook(book);
		
		return ratings;
	}
}
