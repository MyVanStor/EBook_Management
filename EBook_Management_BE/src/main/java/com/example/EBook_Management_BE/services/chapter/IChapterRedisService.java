package com.example.EBook_Management_BE.services.chapter;

import com.example.EBook_Management_BE.entity.Chapter;

public interface IChapterRedisService {
	void clearById(Long id);

	Chapter getChapterById(Long chapterId) throws Exception;

	void saveChapterById(Long chapterId, Chapter chapter) throws Exception;
}
