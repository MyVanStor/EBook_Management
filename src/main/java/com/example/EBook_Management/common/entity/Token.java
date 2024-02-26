package com.example.EBook_Management.common.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "tokens")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Token {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	Long id;
	
	@Column(name = "token", length = 255, unique = true, nullable = false)
	String token;
	
	@Column(name = "token_type", length = 50, nullable = false)
	String tokenType;
	
	@Column(name = "expiration_date")
	LocalDateTime expirationDate;
	
	@Column(name = "revoked", columnDefinition = "TINYINT(1)", nullable = false)
	short revoked;

	@Column(name = "expired", columnDefinition = "TINYINT(1)", nullable = false)
	short expired;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	User user;
}
