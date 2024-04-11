package com.example.EBook_Management_BE.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@Table(name = "books")
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	Long id;
	
	@Column(name = "title", length = 100, nullable = false, unique = true)
	String title;
	
	@Column(name = "summary", length = 255)
	String summary;
	
	@Column(name = "image", length = 255)
	String image;
	
	@Column(name = "type_of_book", length = 100)
	String typeOfBook;
	
	@Column(name = "publishing_year")
	Integer publishingYear;
	
	@Column(name = "number_of_review")
	Long numberOfReview;
	
	@Column(name = "evaluate", columnDefinition = "FLOAT", nullable = false)
	double evaluate;
	
	@Column(name = "thumbnail", length = 255, nullable = false, unique = true)
	String thumbnail;
	
	@Column(name = "price", columnDefinition = "FLOAT")
	double price;
	
	@ManyToMany
	@JoinTable(name = "author_book", joinColumns = { @JoinColumn(name = "book_id") }, inverseJoinColumns = {
			@JoinColumn(name = "author_id") })
	Set<Author> authors;
	
	@ManyToMany
	@JoinTable(name = "painter_book", joinColumns = { @JoinColumn(name = "book_id") }, inverseJoinColumns = {
			@JoinColumn(name = "painter_id") })
	Set<Painter> painters;
	
	@ManyToMany
	@JoinTable(name = "category_book", joinColumns = { @JoinColumn(name = "book_id") }, inverseJoinColumns = {
			@JoinColumn(name = "category_id") })
	Set<Category> categories;
	
	@JsonIgnore
	@OneToMany(mappedBy = "book")
	Set<OrderDetail> orderDetails;
	
	@OneToMany(mappedBy = "book")
	Set<UserBook> userBooks;
	
	@OneToMany(mappedBy = "book")
	Set<Rating> ratings;
}
