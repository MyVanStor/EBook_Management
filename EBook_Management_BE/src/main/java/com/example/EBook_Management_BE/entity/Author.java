package com.example.EBook_Management_BE.entity;

import java.util.Set;

import com.example.EBook_Management_BE.listeners.AuthorListener;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@Table(name = "authors")
@EntityListeners(AuthorListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Author {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name", length = 100, nullable = false, unique = true)
	private String name;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@JsonIgnore
	@ManyToMany(mappedBy = "authors")
	private Set<Book> books;
}
