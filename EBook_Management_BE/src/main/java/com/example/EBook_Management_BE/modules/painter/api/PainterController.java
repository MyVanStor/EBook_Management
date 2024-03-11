package com.example.EBook_Management_BE.modules.painter.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EBook_Management_BE.common.components.LocalizationUtils;
import com.example.EBook_Management_BE.common.entity.Painter;
import com.example.EBook_Management_BE.common.enums.Uri;
import com.example.EBook_Management_BE.common.utils.MessageKeys;
import com.example.EBook_Management_BE.common.utils.ResponseObject;
import com.example.EBook_Management_BE.modules.painter.dto.PainterDTO;
import com.example.EBook_Management_BE.modules.painter.response.PainterResponse;
import com.example.EBook_Management_BE.modules.painter.service.PainterService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = Uri.PAINTER)
@Validated
@RequiredArgsConstructor
public class PainterController {
	private final PainterService painterService;
	private final LocalizationUtils localizationUtils;

	@GetMapping("/{id}")
	public ResponseEntity<ResponseObject> getPainterById(@PathVariable Long id) {
		Painter existingPainter = painterService.getPainterById(id);

		return ResponseEntity.ok(ResponseObject.builder().data(existingPainter)
				.message("Get painter information succesfully").status(HttpStatus.OK).build());
	}

	@PostMapping("")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER') or hasRole('ROLE_SYS-ADMIN')")
	public ResponseEntity<PainterResponse> createPainter(@Valid @RequestBody PainterDTO painterDTO,
			BindingResult result) {
		PainterResponse painterResponse = new PainterResponse();

		if (result.hasErrors()) {
			List<String> errorMessage = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();

			painterResponse.setMessage(localizationUtils.getLocalizedMessage(MessageKeys.INSERT_PAINTER_SUCCESSFULLY));
			painterResponse.setErrors(errorMessage);
			return ResponseEntity.badRequest().body(painterResponse);
		}

		Painter painter = painterService.createPainter(painterDTO);
		painterResponse.setPainter(painter);
		return ResponseEntity.created(null).body(painterResponse);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SYS-ADMIN')")
	public ResponseEntity<ResponseObject> updatePainter(@PathVariable Long id,
			@Valid @RequestBody PainterDTO painterDTO) {
		painterService.updatePainter(id, painterDTO);
		return ResponseEntity.ok(ResponseObject.builder().data(painterService.getPainterById(id))
				.message(localizationUtils.getLocalizedMessage(MessageKeys.UPDATE_PAINTER_SUCCESSFULLY)).build());
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SYS-ADMIN')")
	public ResponseEntity<ResponseObject> deletePainter(@PathVariable Long id) throws Exception {
		painterService.deletePainterById(id);
		return ResponseEntity
				.ok(ResponseObject.builder().status(HttpStatus.OK).message("Delete painter successfully").build());
	}
}
