package com.example.EBook_Management_BE.services.readinghistory;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.EBook_Management_BE.components.LocalizationUtils;
import com.example.EBook_Management_BE.entity.ReadingHistory;
import com.example.EBook_Management_BE.entity.User;
import com.example.EBook_Management_BE.exceptions.DataNotFoundException;
import com.example.EBook_Management_BE.repositories.ReadingHistoryRepository;
import com.example.EBook_Management_BE.utils.MessageExceptionKeys;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReadingHistoryService implements IReadingHistoryService {
	private final ReadingHistoryRepository readingHistoryRepository;
	private final IReadingHistoryRedisService readingHistoryRedisService;

	private final LocalizationUtils localizationUtils;

	@Override
	public ReadingHistory createReadingHistory(ReadingHistory readingHistory) {
		return readingHistoryRepository.save(readingHistory);
	}

	@Override
	public ReadingHistory getReadingHistory(Long id) throws Exception {
		ReadingHistory readingHistory = readingHistoryRedisService.getReadingHistoryById(id);
		if (readingHistory == null) {
			readingHistory = readingHistoryRepository.findById(id).orElseThrow(() -> new DataNotFoundException(
					localizationUtils.getLocalizedMessage(MessageExceptionKeys.READING_HISTORY_NOT_FOUND)));
			
			readingHistoryRedisService.saveReadingHistoryById(id, readingHistory);
		}
		return readingHistory;
	}

	@Override
	public ReadingHistory updateReadingHistory(Long id, ReadingHistory readingHistoryUpdate) throws Exception {
		return readingHistoryRepository.save(readingHistoryUpdate);
	}

	@Override
	public void deleteReadingHistory(ReadingHistory readingHistory) throws Exception {
		readingHistoryRepository.delete(readingHistory);
	}

	@Override
	public Set<ReadingHistory> getAllByUser(User user) {
		return readingHistoryRepository.findByUser(user);
	}

}
