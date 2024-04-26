package com.example.EBook_Management_BE.services.painter;

import com.example.EBook_Management_BE.entity.Painter;
import com.example.EBook_Management_BE.exceptions.DuplicateException;

public interface IPainterService {
	Painter createPainter(Painter painter) throws DuplicateException;
	
	Painter getPainterById(Long painterId) throws Exception;
	
	Painter updatePainter(Long painterId, Painter painterUpdate) throws Exception;
	
	void deletePainterById(Long painterId) throws Exception;
}
