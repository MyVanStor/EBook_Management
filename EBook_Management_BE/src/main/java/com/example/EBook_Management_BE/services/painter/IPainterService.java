package com.example.EBook_Management_BE.services.painter;

import com.example.EBook_Management_BE.dtos.PainterDTO;
import com.example.EBook_Management_BE.entity.Painter;

public interface IPainterService {
	Painter createPainter(PainterDTO painterDTO);
	
	Painter getPainterById(Long painterId);
	
	Painter updatePainter(Long painterId, PainterDTO painterDTO);
	
	Painter deletePainterById(Long painterId) throws Exception;
}
