package com.example.EBook_Management_BE.services.painter;

import com.example.EBook_Management_BE.entity.Painter;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.awt.*;
import java.util.List;

public interface IPainterRedisService {
	void clearById(Long id);
	
	Painter getPainterById(Long painterId) throws Exception;
	
	void savePainterById(Long painterId, Painter painter) throws Exception;

	List<Painter> getAllPainter() throws JsonProcessingException;

	void saveAllPainter(List<Painter> painters) throws JsonProcessingException;
}
