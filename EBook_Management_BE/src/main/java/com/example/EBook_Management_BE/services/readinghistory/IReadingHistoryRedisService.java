package com.example.EBook_Management_BE.services.readinghistory;

import com.example.EBook_Management_BE.entity.ReadingHistory;

public interface IReadingHistoryRedisService {
	void clearById(Long id);

	ReadingHistory getReadingHistoryById(Long id) throws Exception;

	void saveReadingHistoryById(Long id, ReadingHistory readingHistory) throws Exception;
}
