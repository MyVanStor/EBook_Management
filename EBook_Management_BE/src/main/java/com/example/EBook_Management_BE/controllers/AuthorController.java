package com.example.EBook_Management_BE.controllers;

import com.example.EBook_Management_BE.services.author.IAuthorRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.EBook_Management_BE.components.LocalizationUtils;
import com.example.EBook_Management_BE.dtos.AuthorDTO;
import com.example.EBook_Management_BE.entity.Author;
import com.example.EBook_Management_BE.entity.User;
import com.example.EBook_Management_BE.constants.Uri;
import com.example.EBook_Management_BE.mappers.AuthorMapper;
import com.example.EBook_Management_BE.responses.AuthorResponse;
import com.example.EBook_Management_BE.services.author.IAuthorService;
import com.example.EBook_Management_BE.services.user.IUserService;
import com.example.EBook_Management_BE.utils.MessageKeys;
import com.example.EBook_Management_BE.utils.ResponseObject;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = Uri.AUTHOR)
@Validated
@RequiredArgsConstructor
public class AuthorController {
    private final IAuthorService authorService;
    private final IAuthorRedisService authorRedisService;
    private final IUserService userService;
    private final AuthorMapper authorMapper;

    private final LocalizationUtils localizationUtils;

    @GetMapping()
    public ResponseEntity<ResponseObject> getAuthorById(@RequestHeader(name = "author_id") Long authorId) throws Exception {
        Author existingAuthor = authorService.getAuthorById(authorId);

        AuthorResponse authorResponse = authorMapper.mapToAuthorResponse(existingAuthor);

        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .message(localizationUtils.getLocalizedMessage(MessageKeys.AUTHOR_GET_BY_ID_SUCCESSFULLY))
                .data(authorResponse)
                .build());
    }

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<ResponseObject> createAuthor(@Valid @RequestBody AuthorDTO authorDTO) throws Exception {
        User user = userService.getUserById(authorDTO.getUserId());

        Author author = authorMapper.mapToAuthorEntity(authorDTO);
        author.setUser(user);

        Author newAuthor = authorService.createAuthor(author);

        AuthorResponse authorResponse = authorMapper.mapToAuthorResponse(newAuthor);

        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.CREATED)
                .message(localizationUtils.getLocalizedMessage(MessageKeys.AUTHOR_CREATE_SUCCESSFULLY))
                .data(authorResponse)
                .build());
    }

    @PutMapping()
//    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<ResponseObject> updateAuthor(@RequestHeader(name = "author_id") Long authorId,
                                                       @Valid @RequestBody AuthorDTO authorDTO) throws Exception {
        User user = userService.getUserById(authorDTO.getUserId());

        Author author = authorMapper.mapToAuthorEntity(authorDTO);
        author.setUser(user);

        authorService.updateAuthor(authorId, author);

        AuthorResponse authorResponse = authorMapper.mapToAuthorResponse(author);
        authorRedisService.saveAuthorById(authorId, author);

        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .message(localizationUtils.getLocalizedMessage(MessageKeys.AUTHOR_UPDATE_SUCCESSFULLY))
                .data(authorResponse)
                .build());
    }

    @DeleteMapping()
//    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<ResponseObject> deleteAuthor(@RequestHeader(name = "author_id") Long authorId) throws Exception {
        authorService.deleteAuthorById(authorId);

        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .message(localizationUtils.getLocalizedMessage(MessageKeys.AUTHOR_DELETE_SUCCESSFULLY))
                .build());
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseObject> getAllAuthor() throws Exception {
        List<Author> authors = authorService.getAllAuthors();

        List<AuthorResponse> authorResponses = authors.stream()
                .map(authorMapper::mapToAuthorResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ResponseObject.builder()
                .message(localizationUtils.getLocalizedMessage(MessageKeys.AUTHOR_GET_ALL_SUCCESSFULLY))
                .status(HttpStatus.OK)
                .data(authorResponses)
                .build());
    }
}
