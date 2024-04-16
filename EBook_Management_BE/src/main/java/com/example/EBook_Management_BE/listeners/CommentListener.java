package com.example.EBook_Management_BE.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.EBook_Management_BE.entity.Comment;
import com.example.EBook_Management_BE.services.comment.ICommentRedisService;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommentListener {
    private final ICommentRedisService commentRedisService;
    private static final Logger logger = LoggerFactory.getLogger(CommentListener.class);

    @PrePersist
    public void prePersist(Comment comment) {
        logger.info("prePersist");
    }

    @PostPersist // save = persis
    public void postPersist(Comment comment) {
        // Update Redis cache
        logger.info("postPersist");
        commentRedisService.clearById(comment.getId());
    }

    @PreUpdate
    public void preUpdate(Comment comment) {
        // ApplicationEventPublisher.instance().publishEvent(event);
        logger.info("preUpdate");
    }

    @PostUpdate
    public void postUpdate(Comment comment) {
        // Update Redis cache
        logger.info("postUpdate");
        commentRedisService.clearById(comment.getId());
    }

    @PreRemove
    public void preRemove(Comment comment) {
        // ApplicationEventPublisher.instance().publishEvent(event);
        logger.info("preRemove");
    }

    @PostRemove
    public void postRemove(Comment comment) {
        // Update Redis cache
        logger.info("postRemove");
        commentRedisService.clearById(comment.getId());
    }
}
