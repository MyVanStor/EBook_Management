package com.example.EBook_Management_BE.entity;

import com.example.EBook_Management_BE.listeners.TransactionListener;

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
@Table(name = "transactions")
@EntityListeners(TransactionListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "code")
	private String code;

	@Column(name = "value")
	private Double value;

	@Column(name = "type")
	private String type;

	@Column(name = "status")
	private String status;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
}
