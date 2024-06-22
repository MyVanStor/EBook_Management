package com.example.EBook_Management_BE.controllers;

import com.example.EBook_Management_BE.entity.Book;
import com.example.EBook_Management_BE.entity.Chapter;
import com.example.EBook_Management_BE.exceptions.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.EBook_Management_BE.components.LocalizationUtils;
import com.example.EBook_Management_BE.dtos.CommentDTO;
import com.example.EBook_Management_BE.entity.Comment;
import com.example.EBook_Management_BE.entity.User;
import com.example.EBook_Management_BE.constants.CommentReplyType;
import com.example.EBook_Management_BE.constants.Uri;
import com.example.EBook_Management_BE.mappers.CommentMapper;
import com.example.EBook_Management_BE.responses.CommentResponse;
import com.example.EBook_Management_BE.services.book.IBookService;
import com.example.EBook_Management_BE.services.chapter.IChapterService;
import com.example.EBook_Management_BE.services.comment.ICommentRedisService;
import com.example.EBook_Management_BE.services.comment.ICommentService;
import com.example.EBook_Management_BE.services.user.IUserService;
import com.example.EBook_Management_BE.utils.MessageKeys;
import com.example.EBook_Management_BE.utils.ResponseObject;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = Uri.COMMENT)
@Validated
@RequiredArgsConstructor
public class CommentController {
    private final ICommentService commentService;
    private final ICommentRedisService commentRedisService;
    private final IUserService userService;
    private final IBookService bookService;
    private final IChapterService chapterService;

    private final LocalizationUtils localizationUtils;

    @Autowired
    private CommentMapper commentMapper;

    @GetMapping("/{id}")
//    @PreAuthorize("hasRole('ROLE_USER') or hasRole('Role_ADMIN')")
    public ResponseEntity<ResponseObject> getCommmentById(@PathVariable Long id) throws Exception {
        Comment existingComment = commentService.getCommentById(id);

        CommentResponse commentResponse = commentMapper.mapToCommentResponse(existingComment);

        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .message(localizationUtils.getLocalizedMessage(MessageKeys.COMMENT_GET_BY_ID_SUCCESSFULLY))
                .data(commentResponse)
                .build());
    }

    @PostMapping()
//    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<ResponseObject> createComment(@Valid @RequestBody CommentDTO commentDTO) throws Exception {
        User user = userService.getUserById(commentDTO.getUserId());

        if (commentDTO.getReplyType().toUpperCase().equals(CommentReplyType.BOOK)) {
            bookService.getBookById(commentDTO.getReplyId());
        } else if (commentDTO.getReplyType().toUpperCase().equals(CommentReplyType.COMMENT)) {
            commentService.getCommentById(commentDTO.getReplyId());
        } else if (commentDTO.getReplyType().toUpperCase().equals(CommentReplyType.CHAPTER)) {
            chapterService.getChapterById(commentDTO.getReplyId());
        }

        Comment comment = commentMapper.mapToCommentEntity(commentDTO);
        comment.setUser(user);

        Comment newComment = commentService.createComment(comment);
        commentRedisService.saveCommentById(newComment.getId(), newComment);

        CommentResponse commentResponse = commentMapper.mapToCommentResponse(newComment);

        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.CREATED)
                .message(localizationUtils.getLocalizedMessage(MessageKeys.COMMENT_CREATE_SUCCESSFULLY))
                .data(commentResponse)
                .build());
    }

    @PutMapping("/{commentId}")
//    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ResponseObject> updateComment(@PathVariable Long commentId, @Valid @RequestBody CommentDTO commentDTO) throws Exception {
        User user = userService.getUserById(commentDTO.getUserId());

        if (commentDTO.getReplyType().toUpperCase().equals(CommentReplyType.BOOK)) {
            bookService.getBookById(commentDTO.getReplyId());
        } else if (commentDTO.getReplyType().toUpperCase().equals(CommentReplyType.COMMENT)) {
            commentService.getCommentById(commentDTO.getReplyId());
        } else if (commentDTO.getReplyType().toUpperCase().equals(CommentReplyType.CHAPTER)) {
            chapterService.getChapterById(commentDTO.getReplyId());
        }

        Comment comment = commentMapper.mapToCommentEntity(commentDTO);
        comment.setUser(user);

        comment = commentService.updateComment(commentId, comment);
        commentRedisService.saveCommentById(commentId, comment);

        CommentResponse commentResponse = commentMapper.mapToCommentResponse(comment);

        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .message(localizationUtils.getLocalizedMessage(MessageKeys.COMMENT_UPDATE_SUCCESSFULLY))
                .data(commentResponse)
                .build());
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<ResponseObject> deleteComment(@PathVariable Long id) throws Exception {
        commentService.deleteComment(id);

        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .message(localizationUtils.getLocalizedMessage(MessageKeys.COMMENT_DELETE_SUCCESSFULLY))
                .build());
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseObject> getAllComment(@RequestHeader Long id, @RequestHeader String typeComment) throws Exception {
        List<Comment> comments = new ArrayList<>();
        switch (typeComment.toUpperCase()) {
            case CommentReplyType.BOOK -> {
                Book book = bookService.getBookById(id);
            }
            case CommentReplyType.COMMENT -> {
                Comment comment = commentService.getCommentById(id);
            }
            case CommentReplyType.CHAPTER -> {
                Chapter chapter = chapterService.getChapterById(id);
            }
        }
        comments = commentService.getAllComment(id, typeComment);

        List<CommentResponse> commentResponses = new ArrayList<>();
        for (Comment comment : comments) {
            commentResponses.add(commentMapper.mapToCommentResponse(comment));
        }

        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .message(localizationUtils.getLocalizedMessage(MessageKeys.COMMENT_GET_ALL_SUCCESFULLY))
                .data(commentResponses)
                .build());
    }
}
