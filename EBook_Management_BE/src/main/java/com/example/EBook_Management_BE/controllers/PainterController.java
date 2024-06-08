package com.example.EBook_Management_BE.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.EBook_Management_BE.components.LocalizationUtils;
import com.example.EBook_Management_BE.dtos.PainterDTO;
import com.example.EBook_Management_BE.entity.Painter;
import com.example.EBook_Management_BE.entity.User;
import com.example.EBook_Management_BE.constants.Uri;
import com.example.EBook_Management_BE.mappers.PainterMapper;
import com.example.EBook_Management_BE.responses.PainterResponse;
import com.example.EBook_Management_BE.services.painter.IPainterRedisService;
import com.example.EBook_Management_BE.services.painter.IPainterService;
import com.example.EBook_Management_BE.services.user.IUserService;
import com.example.EBook_Management_BE.utils.MessageKeys;
import com.example.EBook_Management_BE.utils.ResponseObject;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping()
    public ResponseEntity<ResponseObject> getPainterById(@RequestHeader(name = "painter_id") Long painterId) throws Exception {
        Painter existingPainter = painterService.getPainterById(painterId);

        PainterResponse painterResponse = painterMapper.mapToPainterResponse(existingPainter);

        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .message(localizationUtils.getLocalizedMessage(MessageKeys.PAINTER_GET_BY_ID_SUCCESSFULLY))
                .data(painterResponse)
                .build());
    }

    @PostMapping()
//    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
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

    @PutMapping()
//    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<ResponseObject> updatePainter(@RequestHeader(name = "painter_id") Long painterId,
                                                        @Valid @RequestBody PainterDTO painterDTO) throws Exception {
        User user = userService.getUserById(painterDTO.getUserId());

        Painter painter = painterMapper.mapToPainterEntity(painterDTO);
        painter.setUser(user);

        painterService.updatePainter(painterId, painter);
        painterRedisService.savePainterById(painterId, painter);

        PainterResponse painterResponse = painterMapper.mapToPainterResponse(painter);

        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .message(localizationUtils.getLocalizedMessage(MessageKeys.PAINTER_UPDATE_SUCCESSFULLY))
                .data(painterResponse)
                .build());
    }

    @DeleteMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<ResponseObject> deletePainter(@RequestHeader(name = "painter_id") Long painterId) throws Exception {
        painterService.deletePainterById(painterId);

        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .message(localizationUtils.getLocalizedMessage(MessageKeys.PAINTER_DELETE_SUCCESSFULLY))
                .build());
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseObject> getAllPainter() throws Exception {
        List<Painter> painters = painterService.getAllPainter();

        List<PainterResponse> painterResponses = painters.stream()
                .map(painterMapper::mapToPainterResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ResponseObject.builder()
                .message(localizationUtils.getLocalizedMessage(MessageKeys.PAINTER_GET_ALL_SUCCESSFULLY))
                .status(HttpStatus.OK)
                .data(painterResponses)
                .build());
    }
}
