package com.example.EBook_Management_BE.services.painter;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.EBook_Management_BE.components.LocalizationUtils;
import com.example.EBook_Management_BE.entity.Painter;
import com.example.EBook_Management_BE.exceptions.DataNotFoundException;
import com.example.EBook_Management_BE.exceptions.DuplicateException;
import com.example.EBook_Management_BE.exceptions.DeleteException;
import com.example.EBook_Management_BE.repositories.BookRepository;
import com.example.EBook_Management_BE.repositories.PainterRepository;
import com.example.EBook_Management_BE.utils.MessageExceptionKeys;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PainterService implements IPainterService {
	private final PainterRepository painterRepository;
	private final BookRepository bookRepository;
	
	private final LocalizationUtils localizationUtils;
	
	@Override
	@Transactional
	public Painter createPainter(Painter painter) throws DuplicateException {
		if (painterRepository.existsByNameAndUser(painter.getName(), painter.getUser())) {
			throw new DuplicateException(
					localizationUtils.getLocalizedMessage(MessageExceptionKeys.PAINTER_DUPLICATE_PAINTER));
		}

		return painterRepository.save(painter);
	}

	@Override
	public Painter getPainterById(Long painterId) throws DataNotFoundException {
		return painterRepository.findById(painterId).orElseThrow(() -> new DataNotFoundException(
				localizationUtils.getLocalizedMessage(MessageExceptionKeys.PAINTER_NOT_FOUND)));
	}

	@Override
	@Transactional
	public Painter updatePainter(Long painterId, Painter painterUpdate) throws Exception {
		Painter exitstingPainter = getPainterById(painterId);

		painterUpdate.setId(exitstingPainter.getId());
		painterRepository.save(painterUpdate);

		return painterUpdate;
	}

	@Override
	@Transactional
	public void deletePainterById(Long painterId) throws Exception {
		Painter painter = getPainterById(painterId);
		Set<Painter> painters = new HashSet<Painter>();
		painters.add(painter);

		if (bookRepository.existsByPainters(painters)) {
			throw new DeleteException(
					localizationUtils.getLocalizedMessage(MessageExceptionKeys.PAINTER_DELETE_HAVE_ASSOCIATED_BOOK));
		} else {
			painterRepository.deleteById(painterId);
		}
	}

}
