package com.example.EBook_Management_BE.entity;

import java.util.Set;

import com.example.EBook_Management_BE.listeners.ChapterListener;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "chapters")
@EntityListeners(ChapterListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Chapter extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "ordinal_number")
	private int ordinalNumber;

	@Column(name = "thumbnail")
	private String thumbnail;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "book_id")
	private Book book;

	@JsonIgnore
	@OneToMany(mappedBy = "chapter")
	private Set<ReadingHistory> readingHistories;
}
