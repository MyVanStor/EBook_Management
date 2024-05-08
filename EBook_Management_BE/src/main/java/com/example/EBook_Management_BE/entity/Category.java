package com.example.EBook_Management_BE.entity;

import java.util.Set;

import com.example.EBook_Management_BE.listeners.CategoryListener;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "categories")
@EntityListeners(CategoryListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name", length = 100, nullable = false, unique = true)
	private String name;

	@Column(name = "description", length = 255)
	private String description;

	@JsonIgnore
	@ManyToMany(mappedBy = "categories")
	private Set<Book> books;
}
