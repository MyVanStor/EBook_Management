package com.example.EBook_Management_BE.modules.rating.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EBook_Management_BE.common.components.LocalizationUtils;
import com.example.EBook_Management_BE.common.entity.Rating;
import com.example.EBook_Management_BE.common.enums.Uri;
import com.example.EBook_Management_BE.common.utils.MessageKeys;
import com.example.EBook_Management_BE.common.utils.ResponseObject;
import com.example.EBook_Management_BE.modules.rating.dto.RatingDTO;
import com.example.EBook_Management_BE.modules.rating.response.RatingResponse;
import com.example.EBook_Management_BE.modules.rating.service.RatingService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = Uri.RATING)
@Validated
@RequiredArgsConstructor
public class RatingController {
	private final RatingService ratingService;
	private final LocalizationUtils localizationUtils;

	@PostMapping("")
	public ResponseEntity<RatingResponse> createRating(@Valid @RequestBody RatingDTO ratingDTO, BindingResult result) {
		RatingResponse ratingResponse = new RatingResponse();

		if (result.hasErrors()) {
			List<String> errorMessages = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();

			ratingResponse.setMessage(localizationUtils.getLocalizedMessage(MessageKeys.INSERT_CATEGORY_SUCCESSFULLY));
			ratingResponse.setErrors(errorMessages);

			return ResponseEntity.badRequest().body(ratingResponse);
		}

		Rating rating = ratingService.createRating(ratingDTO);
		ratingResponse.setRating(rating);
		return ResponseEntity.created(null).body(ratingResponse);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ResponseObject> updateRating(@PathVariable Long id, @Valid @RequestBody RatingDTO ratingDTO) {
		ratingService.updateRating(id, ratingDTO);
		return ResponseEntity.ok(ResponseObject.builder().data(ratingService.getRatingById(id))
				.message(localizationUtils.getLocalizedMessage(MessageKeys.UPDATE_CATEGORY_SUCCESSFULLY)).build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseObject> deleteRating(@PathVariable Long id) throws Exception {
		ratingService.deleteRating(id);
		return ResponseEntity
				.ok(ResponseObject.builder().status(HttpStatus.OK).message("Delete rating successfully").build());
	}
	
	@GetMapping("/all/{bookId}")
	public ResponseEntity<ResponseObject> getAllRatingByBookId(@PathVariable Long bookId) {
		int countRating = ratingService.getAllRatingByBookId(bookId);
		
		return ResponseEntity.ok(ResponseObject.builder()
				.status(HttpStatus.OK)
				.message(String.format("Get all ratings book with book_id = %d success", bookId))
				.data(countRating)
				.build());
	}
}
