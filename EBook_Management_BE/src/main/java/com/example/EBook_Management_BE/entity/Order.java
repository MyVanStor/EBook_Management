package com.example.EBook_Management_BE.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	Long id;
	
	@Column(name = "status", length = 100, nullable = false)
	String status;
	
	@Column(name = "total_money", columnDefinition = "FLOAT", nullable = false)
	double totalMoney;
	
	@Column(name = "payment_method", length = 100)
	String paymentMethod;
	
	@OneToMany(mappedBy = "order")
	Set<OrderDetail> orderDetails;
	
	@JsonIgnore
	@ManyToOne()
	@JoinColumn(name = "user_id")
	User user;
}
