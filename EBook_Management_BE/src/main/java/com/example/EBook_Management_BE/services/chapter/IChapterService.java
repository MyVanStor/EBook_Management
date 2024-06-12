package com.example.EBook_Management_BE.services.chapter;

import com.example.EBook_Management_BE.entity.Book;
import com.example.EBook_Management_BE.entity.Chapter;

import java.util.List;

public interface IChapterService {
	Chapter getChapterById(Long chapterId) throws Exception;
	
	Chapter createChapter(Chapter chapter);
	
	Chapter updateChapter(Long chapterId, Chapter chapter) throws Exception;
	
	void deleteChapterById(Long chapterId) throws Exception;

	List<Chapter> getAllChapterByBook(Book book) throws Exception;
}
