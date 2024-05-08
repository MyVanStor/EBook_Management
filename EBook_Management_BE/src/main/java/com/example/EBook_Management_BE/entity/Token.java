package com.example.EBook_Management_BE.entity;

import java.time.LocalDateTime;

import com.example.EBook_Management_BE.listeners.TokenListener;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "tokens")
@EntityListeners(TokenListener.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Token {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "token", unique = true, nullable = false)
	private String token;

	@Column(name = "token_type", length = 50, nullable = false)
	private String tokenType;

	@Column(name = "refresh_token", length = 255)
	private String refreshToken;

	@Column(name = "expiration_date")
	private LocalDateTime expirationDate;

	@Column(name = "refresh_expiration_date")
	private LocalDateTime refreshExpirationDate;

	@Column(name = "is_mobile", columnDefinition = "TINYINT(1)")
	private boolean isMobile;

	@Column(name = "revoked", columnDefinition = "TINYINT(1)", nullable = false)
	private boolean revoked;

	@Column(name = "expired", columnDefinition = "TINYINT(1)", nullable = false)
	private boolean expired;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
}
