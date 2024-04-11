package com.example.EBook_Management_BE.services.painter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.EBook_Management_BE.dtos.PainterDTO;
import com.example.EBook_Management_BE.entity.Book;
import com.example.EBook_Management_BE.entity.Painter;
import com.example.EBook_Management_BE.repositories.BookRepository;
import com.example.EBook_Management_BE.repositories.PainterRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PainterService implements IPainterService {
	private final PainterRepository painterRepository;
	private final BookRepository bookRepository;

	@Override
	@Transactional
	public Painter createPainter(PainterDTO painterDTO) {
		Painter newPainter = Painter.builder().name(painterDTO.getName()).build();

		return painterRepository.save(newPainter);
	}

	@Override
	public Painter getPainterById(Long painterId) {
		return painterRepository.findById(painterId).orElseThrow(() -> new RuntimeException("Painter not found"));
	}

	@Override
	@Transactional
	public Painter updatePainter(Long painterId, PainterDTO painterDTO) {
		Painter exitstingPainter = getPainterById(painterId);

		exitstingPainter.setName(painterDTO.getName());
		painterRepository.save(exitstingPainter);

		return exitstingPainter;
	}

	@Override
	@Transactional
	public Painter deletePainterById(Long painterId) throws Exception {
		Painter painter = painterRepository.findById(painterId)
				.orElseThrow(() -> new ChangeSetPersister.NotFoundException());
		
		Set<Painter> painters = new HashSet<>();
		painters.add(painter);
		
		List<Book> books = bookRepository.findByPainters(painters);
		if (!books.isEmpty()) {
			throw new IllegalStateException("Cannot delete painter with associated books");
		} else {
			painterRepository.deleteById(painterId);
			return painter;
		}
	}

}
