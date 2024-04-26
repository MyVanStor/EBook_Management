package com.example.EBook_Management_BE.services.chapter;

import org.springframework.stereotype.Service;

import com.example.EBook_Management_BE.components.LocalizationUtils;
import com.example.EBook_Management_BE.entity.Chapter;
import com.example.EBook_Management_BE.exceptions.DataNotFoundException;
import com.example.EBook_Management_BE.repositories.ChapterRepository;
import com.example.EBook_Management_BE.utils.MessageExceptionKeys;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChapterService implements IChapterService {
	private final ChapterRepository chapterRepository;
	private final IChapterRedisService chapterRedisService;

	private final LocalizationUtils localizationUtils;

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
	public void deleteChapterById(Long chapterId) {
		chapterRepository.deleteById(chapterId);
	}

}
