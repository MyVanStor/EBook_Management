package com.example.EBook_Management_BE.entity;

import java.util.Set;

import com.example.EBook_Management_BE.listeners.BookListener;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "books")
@EntityListeners(BookListener.class)
@AllArgsConstructor
@NoArgsConstructor
public class Book extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "title", length = 100, nullable = false, unique = true)
	private String title;

	@Column(name = "summary", length = 255)
	private String summary;

	@Column(name = "image", length = 255)
	private String image;

	@Column(name = "type_of_book", length = 100)
	private String typeOfBook;

	@Column(name = "status")
	private String status;

	@Column(name = "publishing_year")
	private Integer publishingYear;

	@Column(name = "price", columnDefinition = "FLOAT")
	private double price;

	@Column(name = "number_reads")
	private Long numberReads;

	@Column(name = "author")
	private String author;

	@Column(name = "painter")
	private String painter;

	@ManyToMany
	@JoinTable(name = "category_book", joinColumns = { @JoinColumn(name = "book_id") }, inverseJoinColumns = {
			@JoinColumn(name = "category_id") })
	private Set<Category> categories;

	@JsonIgnore
	@OneToMany(mappedBy = "book")
	private Set<OrderDetail> orderDetails;

	@OneToMany(mappedBy = "book")
	private Set<UserBook> userBooks;

	@JsonIgnore
	@OneToMany(mappedBy = "book")
	private Set<Rating> ratings;

	@OneToMany(mappedBy = "book")
	private Set<Chapter> chapters;

	@OneToMany(mappedBy = "book")
	private Set<ReadingHistory> readingHistories;
}
