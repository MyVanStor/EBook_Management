package com.example.EBook_Management_BE.services.chapter;

import com.example.EBook_Management_BE.entity.Chapter;

import java.util.List;

public interface IChapterRedisService {
	void clearById(Chapter chapter);

	Chapter getChapterById(Long chapterId) throws Exception;

	void saveChapterById(Long chapterId, Chapter chapter) throws Exception;

	List<Chapter> getAllChapterByBookId(Long bookId) throws Exception;

	void saveAllChapterByBookId(Long bookId, List<Chapter> chapters) throws Exception;
}
