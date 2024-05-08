package com.example.EBook_Management_BE.entity;

import com.example.EBook_Management_BE.listeners.FollowListener;

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
@Getter
@Setter
@Table(name = "follows")
@EntityListeners(FollowListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Follow {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "following")
	private Long following;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
}
