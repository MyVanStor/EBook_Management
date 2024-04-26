package com.example.EBook_Management_BE.controllers;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.EBook_Management_BE.components.LocalizationUtils;
import com.example.EBook_Management_BE.dtos.ReadingHistoryDTO;
import com.example.EBook_Management_BE.entity.Book;
import com.example.EBook_Management_BE.entity.Chapter;
import com.example.EBook_Management_BE.entity.ReadingHistory;
import com.example.EBook_Management_BE.entity.User;
import com.example.EBook_Management_BE.enums.Uri;
import com.example.EBook_Management_BE.mappers.ReadingHistoryMapper;
import com.example.EBook_Management_BE.responses.ReadingHistoryResponse;
import com.example.EBook_Management_BE.services.book.IBookRedisService;
import com.example.EBook_Management_BE.services.book.IBookService;
import com.example.EBook_Management_BE.services.chapter.IChapterRedisService;
import com.example.EBook_Management_BE.services.chapter.IChapterService;
import com.example.EBook_Management_BE.services.readinghistory.IReadingHistoryRedisService;
import com.example.EBook_Management_BE.services.readinghistory.IReadingHistoryService;
import com.example.EBook_Management_BE.services.user.IUserRedisService;
import com.example.EBook_Management_BE.services.user.IUserService;
import com.example.EBook_Management_BE.utils.MessageKeys;
import com.example.EBook_Management_BE.utils.ResponseObject;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = Uri.READING_HISTORY)
@RequiredArgsConstructor
public class ReadingHistoryController {
	private final IReadingHistoryRedisService readingHistoryRedisService;
	private final IReadingHistoryService readingHistoryService;
	private final IUserRedisService userRedisService;
	private final IUserService userService;
	private final IChapterRedisService chapterRedisService;
	private final IChapterService chapterService;
	private final IBookRedisService bookRedisService;
	private final IBookService bookService;
	
	private final LocalizationUtils localizationUtils;
	
	@Autowired
	private ReadingHistoryMapper readingHistoryMapper;

	@PostMapping()
	@PreAuthorize("hasRole('ROLE_USER')")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<ResponseObject> createReadingHistory(@Valid @RequestBody ReadingHistoryDTO readingHistoryDTO)
			throws Exception {
		User user = userRedisService.getUserById(readingHistoryDTO.getUserId());
		if (user == null) {
			user = userService.getUserById(readingHistoryDTO.getUserId());

			userRedisService.saveUserById(user.getId(), user);
		}

		Chapter chapter = chapterRedisService.getChapterById(readingHistoryDTO.getChapterId());
		if (chapter == null) {
			chapter = chapterService.getChapterById(readingHistoryDTO.getChapterId());

			chapterRedisService.saveChapterById(readingHistoryDTO.getChapterId(), chapter);
		}
		
		Book book = bookRedisService.getBookById(readingHistoryDTO.getBookId());
		if (book == null) {
			book = bookService.getBookById(readingHistoryDTO.getBookId());
			
			bookRedisService.saveBookById(book.getId(), book);
		}

		ReadingHistory readingHistory = ReadingHistory.builder()
				.user(user)
				.chapter(chapter)
				.book(book)
				.build();

		ReadingHistory newReadingHistory = readingHistoryService.createReadingHistory(readingHistory);
		readingHistoryRedisService.saveReadingHistoryById(newReadingHistory.getId(), newReadingHistory);

		ReadingHistoryResponse readingHistoryResponse = readingHistoryMapper.mapToReadingHistoryResponse(newReadingHistory);

		return ResponseEntity.ok(ResponseObject.builder()
				.status(HttpStatus.CREATED)
				.message(localizationUtils.getLocalizedMessage(MessageKeys.READING_HISTORY_CREATE_SUCCESSFULLY))
				.data(readingHistoryResponse)
				.build());
	}
	
	@PutMapping("/{id}/{chapterId}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<ResponseObject> updateReadingHistory(@PathVariable Long id,
			@PathVariable Long chapterId) throws Exception {
		ReadingHistory readingHistory = readingHistoryRedisService.getReadingHistoryById(id);
		if (readingHistory == null) {
			readingHistory = readingHistoryService.getReadingHistory(id);
		}
		
		Chapter chapter = chapterRedisService.getChapterById(chapterId);
		if (chapter == null) {
			chapter = chapterService.getChapterById(chapterId);

			chapterRedisService.saveChapterById(chapterId, chapter);
		}
		
		readingHistory.setChapter(chapter);
		readingHistory = readingHistoryService.updateReadingHistory(id, readingHistory);
		readingHistoryRedisService.saveReadingHistoryById(id, readingHistory);
		
		ReadingHistoryResponse readingHistoryResponse = readingHistoryMapper.mapToReadingHistoryResponse(readingHistory);
		
		return ResponseEntity.ok(ResponseObject.builder()
				.status(HttpStatus.OK)
				.message(localizationUtils.getLocalizedMessage(MessageKeys.READING_HISTORY_UPDATE_SUCCESSFULLY))
				.data(readingHistoryResponse)
				.build());
	}
	
	@GetMapping("/{userId}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<ResponseObject> getAllHistoryByUser(@PathVariable Long userId) throws Exception {
		User user = userRedisService.getUserById(userId);
		if (user == null) {
			user = userService.getUserById(userId);

			userRedisService.saveUserById(user.getId(), user);
		}
		
		Set<ReadingHistory> readingHistories = readingHistoryService.getAllByUser(user);
		
		Set<ReadingHistoryResponse> readingHistoryResponses = readingHistories.stream()
				.map(readingHistory -> readingHistoryMapper.mapToReadingHistoryResponse(readingHistory))
				.collect(Collectors.toSet());
		
		return ResponseEntity.ok(ResponseObject.builder()
				.status(HttpStatus.OK)
				.message(localizationUtils.getLocalizedMessage(MessageKeys.READING_HISTORY_GET_ALL_BY_USER_SUCCESSFULLY))
				.data(readingHistoryResponses)
				.build());
	}
}
