package com.example.EBook_Management_BE.services.painter;

import com.example.EBook_Management_BE.entity.Painter;

public interface IPainterRedisService {
	void clearById(Long id);
	
	Painter getPainterById(Long painterId) throws Exception;
	
	void savePainterById(Long painterId, Painter painter) throws Exception;
}
