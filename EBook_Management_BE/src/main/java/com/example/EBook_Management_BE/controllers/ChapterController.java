package com.example.EBook_Management_BE.controllers;

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
import com.example.EBook_Management_BE.dtos.ChapterDTO;
import com.example.EBook_Management_BE.entity.Book;
import com.example.EBook_Management_BE.entity.Chapter;
import com.example.EBook_Management_BE.constants.Uri;
import com.example.EBook_Management_BE.mappers.ChapterMapper;
import com.example.EBook_Management_BE.responses.ChapterResponse;
import com.example.EBook_Management_BE.services.book.IBookService;
import com.example.EBook_Management_BE.services.chapter.IChapterRedisService;
import com.example.EBook_Management_BE.services.chapter.IChapterService;
import com.example.EBook_Management_BE.utils.MessageKeys;
import com.example.EBook_Management_BE.utils.ResponseObject;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = Uri.CHAPTER)
@Validated
@RequiredArgsConstructor
public class ChapterController {
	private final IChapterService chapterService;
	private final IChapterRedisService chapterRedisService;
	private final IBookService bookService;
	
	private final LocalizationUtils localizationUtils;
	
	@Autowired
	private ChapterMapper chapterMapper;
	
	@PostMapping()
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<ResponseObject> createChapter(@Valid @RequestBody ChapterDTO chapterDTO) throws Exception {
		Book book = bookService.getBookById(chapterDTO.getBookId());
		
		Chapter chapter = chapterMapper.mapToChapterEntity(chapterDTO);
		chapter.setBook(book);
		
		Chapter newChapter = chapterService.createChapter(chapter);
		chapterRedisService.saveChapterById(newChapter.getId(), newChapter);
		
		ChapterResponse chapterResponse = chapterMapper.mapToChapterResponse(newChapter);
		
		return ResponseEntity.ok(ResponseObject.builder()
				.status(HttpStatus.CREATED)
				.message(localizationUtils.getLocalizedMessage(MessageKeys.CHAPTER_CREATE_SUCCESSFULLY))
				.data(chapterResponse)
				.build());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseObject> getChapterById(@PathVariable Long id) throws Exception {
		Chapter existingChapter = chapterService.getChapterById(id);
		
		ChapterResponse chapterResponse = chapterMapper.mapToChapterResponse(existingChapter);
		
		return ResponseEntity.ok(ResponseObject.builder()
				.status(HttpStatus.OK)
				.message(localizationUtils.getLocalizedMessage(MessageKeys.CHAPTER_GET_BY_ID_SUCCESSFULLY))
				.data(chapterResponse)
				.build());
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public ResponseEntity<ResponseObject> updateChapter(@PathVariable Long id,
			@Valid @RequestBody ChapterDTO chapterDTO) throws Exception {
		Book book = bookService.getBookById(chapterDTO.getBookId());
		
		Chapter chapter = chapterMapper.mapToChapterEntity(chapterDTO);
		chapter.setBook(book);
		
		chapterService.updateChapter(id, chapter);
		chapterRedisService.saveChapterById(id, chapter);
		
		ChapterResponse chapterResponse = chapterMapper.mapToChapterResponse(chapter);
		
		return ResponseEntity.ok(ResponseObject.builder()
				.status(HttpStatus.OK)
				.message(localizationUtils.getLocalizedMessage(MessageKeys.CHAPTER_UPDATE_SUCCESSFULLY))
				.data(chapterResponse)
				.build());
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public ResponseEntity<ResponseObject> deleteAuthor(@PathVariable Long id) throws Exception {
		chapterService.deleteChapterById(id);
		
		return ResponseEntity
				.ok(ResponseObject.builder()
						.status(HttpStatus.OK)
						.message(localizationUtils.getLocalizedMessage(MessageKeys.CHAPTER_DELETE_SUCCESSFULLY))
						.build());
	}
}
