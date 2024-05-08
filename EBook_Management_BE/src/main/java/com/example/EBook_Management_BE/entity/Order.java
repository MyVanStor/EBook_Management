package com.example.EBook_Management_BE.entity;

import java.util.Set;

import com.example.EBook_Management_BE.listeners.OrderListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "orders")
@EntityListeners(OrderListener.class)
@AllArgsConstructor
@NoArgsConstructor
public class Order extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "status", length = 100, nullable = false)
	private String status;

	@Column(name = "total_money", columnDefinition = "FLOAT", nullable = false)
	private double totalMoney;

	@Column(name = "payment_method", length = 100)
	private String paymentMethod;

	@OneToMany(mappedBy = "order")
	private Set<OrderDetail> orderDetails;

	@ManyToOne()
	@JoinColumn(name = "user_id")
	private User user;
}
