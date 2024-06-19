package com.example.EBook_Management_BE.services.chapter;

import com.example.EBook_Management_BE.entity.Book;
import com.example.EBook_Management_BE.entity.ReadingHistory;
import com.example.EBook_Management_BE.repositories.ReadingHistoryRepository;
import org.springframework.stereotype.Service;

import com.example.EBook_Management_BE.components.LocalizationUtils;
import com.example.EBook_Management_BE.entity.Chapter;
import com.example.EBook_Management_BE.exceptions.DataNotFoundException;
import com.example.EBook_Management_BE.repositories.ChapterRepository;
import com.example.EBook_Management_BE.utils.MessageExceptionKeys;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChapterService implements IChapterService {
	private final ChapterRepository chapterRepository;
	private final IChapterRedisService chapterRedisService;

	private final LocalizationUtils localizationUtils;
	private final ReadingHistoryRepository readingHistoryRepository;

	@Override
	public Chapter getChapterById(Long chapterId) throws Exception {
		Chapter chapter = chapterRedisService.getChapterById(chapterId);
		if (chapter == null) {
			chapter = chapterRepository.findById(chapterId).orElseThrow(() -> new DataNotFoundException(
					localizationUtils.getLocalizedMessage(MessageExceptionKeys.CHAPTER_NOT_FOUND)));
			
			chapterRedisService.saveChapterById(chapterId, chapter);
		}
		return chapter;
	}

	@Override
	public Chapter createChapter(Chapter chapter) {
		return chapterRepository.save(chapter);
	}

	@Override
	public Chapter updateChapter(Long chapterId, Chapter chapter) throws Exception {
		Chapter existingChapter = getChapterById(chapterId);

		chapter.setId(existingChapter.getId());
		chapter.setCreatedAt(existingChapter.getCreatedAt());
		chapterRepository.save(chapter);

		return chapter;
	}

	@Override
	public void deleteChapterById(Long chapterId) throws Exception {
		Chapter chapter = getChapterById(chapterId);

		List<ReadingHistory> chapters = readingHistoryRepository.findByChapter(chapter);
		readingHistoryRepository.deleteAll(chapters);

		chapterRepository.deleteById(chapterId);
	}

	@Override
	public List<Chapter> getAllChapterByBook(Book book) throws Exception {
		List<Chapter> chapters = chapterRedisService.getAllChapterByBookId(book.getId());
		if (chapters == null) {
			chapters = chapterRepository.findByBook(book);

			chapterRedisService.saveAllChapterByBookId(book.getId(), chapters);
		}

		return chapters;
	}

}
