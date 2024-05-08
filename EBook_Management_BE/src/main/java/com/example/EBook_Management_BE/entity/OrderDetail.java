package com.example.EBook_Management_BE.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "order_details")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "price", columnDefinition = "FLOAT")
	private double price;

	@ManyToOne
	@JoinColumn(name = "book_id")
	private Book book;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;
}
