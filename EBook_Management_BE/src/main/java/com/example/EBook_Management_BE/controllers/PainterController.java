package com.example.EBook_Management_BE.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.EBook_Management_BE.components.LocalizationUtils;
import com.example.EBook_Management_BE.dtos.PainterDTO;
import com.example.EBook_Management_BE.entity.Painter;
import com.example.EBook_Management_BE.entity.User;
import com.example.EBook_Management_BE.enums.Uri;
import com.example.EBook_Management_BE.mappers.PainterMapper;
import com.example.EBook_Management_BE.responses.PainterResponse;
import com.example.EBook_Management_BE.services.painter.IPainterRedisService;
import com.example.EBook_Management_BE.services.painter.IPainterService;
import com.example.EBook_Management_BE.services.user.IUserService;
import com.example.EBook_Management_BE.utils.MessageKeys;
import com.example.EBook_Management_BE.utils.ResponseObject;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = Uri.PAINTER)
@Validated
@RequiredArgsConstructor
public class PainterController {
	private final IPainterService painterService;
	private final IPainterRedisService painterRedisService;
	private final IUserService userService;
	
	private final LocalizationUtils localizationUtils;
	
	@Autowired
	private PainterMapper painterMapper;

	@GetMapping("/{id}")
	public ResponseEntity<ResponseObject> getPainterById(@PathVariable Long id) throws Exception {
		Painter existingPainter = painterService.getPainterById(id);
		
		PainterResponse painterResponse = painterMapper.mapToPainterResponse(existingPainter);
		
		return ResponseEntity.ok(ResponseObject.builder()
				.status(HttpStatus.OK)
				.message(localizationUtils.getLocalizedMessage(MessageKeys.PAINTER_GET_BY_ID_SUCCESSFULLY))
				.data(painterResponse)
				.build());
	}

	@PostMapping()
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<ResponseObject> createPainter(@Valid @RequestBody PainterDTO painterDTO) throws Exception {
		User user = userService.getUserById(painterDTO.getUserId());

		Painter painter = painterMapper.mapToPainterEntity(painterDTO);
		painter.setUser(user);
		
		Painter newPainter = painterService.createPainter(painter);
		painterRedisService.savePainterById(newPainter.getId(), newPainter);
		
		PainterResponse painterResponse = painterMapper.mapToPainterResponse(newPainter);
		
		return ResponseEntity.ok(ResponseObject.builder()
				.status(HttpStatus.CREATED)
				.message(localizationUtils.getLocalizedMessage(MessageKeys.PAINTER_CREATE_SUCCESSFULLY))
				.data(painterResponse)
				.build());
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public ResponseEntity<ResponseObject> updatePainter(@PathVariable Long id,
			@Valid @RequestBody PainterDTO painterDTO) throws Exception {
		User user = userService.getUserById(painterDTO.getUserId());
		
		Painter painter = painterMapper.mapToPainterEntity(painterDTO);
		painter.setUser(user);
		
		painterService.updatePainter(id, painter);
		painterRedisService.savePainterById(id, painter);
		
		PainterResponse painterResponse = painterMapper.mapToPainterResponse(painter);
		
		return ResponseEntity.ok(ResponseObject.builder()
				.status(HttpStatus.OK)
				.message(localizationUtils.getLocalizedMessage(MessageKeys.PAINTER_UPDATE_SUCCESSFULLY))
				.data(painterResponse)
				.build());
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public ResponseEntity<ResponseObject> deletePainter(@PathVariable Long id) throws Exception {
		painterService.deletePainterById(id);
		
		return ResponseEntity
				.ok(ResponseObject.builder()
						.status(HttpStatus.OK)
						.message(localizationUtils.getLocalizedMessage(MessageKeys.PAINTER_DELETE_SUCCESSFULLY))
						.build());
	}
}
