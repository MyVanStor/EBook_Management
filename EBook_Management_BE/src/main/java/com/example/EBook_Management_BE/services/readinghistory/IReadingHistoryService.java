package com.example.EBook_Management_BE.services.readinghistory;

import java.util.Set;

import com.example.EBook_Management_BE.entity.ReadingHistory;
import com.example.EBook_Management_BE.entity.User;
import com.example.EBook_Management_BE.exceptions.DuplicateException;

public interface IReadingHistoryService {
	ReadingHistory createReadingHistory(ReadingHistory readingHistory) throws DuplicateException;
	
	ReadingHistory getReadingHistory(Long id) throws Exception;
	
	ReadingHistory updateReadingHistory(Long id, ReadingHistory readingHistoryUpdate) throws Exception;
	
	void deleteReadingHistory(ReadingHistory readingHistory) throws Exception;
	
	Set<ReadingHistory> getAllByUser(User user);
}
