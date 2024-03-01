package com.example.EBook_Management_BE.modules.painter.service;

import com.example.EBook_Management_BE.common.entity.Painter;
import com.example.EBook_Management_BE.modules.painter.dto.PainterDTO;

public interface IPainterService {
	Painter createPainter(PainterDTO painterDTO);
	
	Painter getPainterById(Long painterId);
	
	Painter updatePainter(Long painterId, PainterDTO painterDTO);
	
	Painter deletePainterById(Long painterId) throws Exception;
}
