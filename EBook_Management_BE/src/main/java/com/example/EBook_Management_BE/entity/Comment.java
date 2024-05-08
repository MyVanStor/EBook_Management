package com.example.EBook_Management_BE.entity;

import com.example.EBook_Management_BE.listeners.CommentListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "comments")
@EntityListeners(CommentListener.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "comment", length = 255, nullable = false)
	private String comment;

	@Column(name = "reply_type", nullable = false)
	private String replyType;

	@Column(name = "reply_id", nullable = false)
	private Long replyId;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
}
