package com.example.EBook_Management_BE.common.entity;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	Long id;

	@Column(name = "fullname", length = 100)
	String fullname;

	@Column(name = "password", length = 20, nullable = false)
	String password;

	@Column(name = "link_avatar", length = 255, unique = true)
	String linkAvatar;

	@Column(name = "phone_number", length = 10, unique = true)
	String phoneNumber;

	@Column(name = "gender", columnDefinition = "TINYINT(1)")
	short gender;

	@Column(name = "budget", columnDefinition = "FLOAT", nullable = false)
	double budget;
	
	@Column(name = "date_of_birth", columnDefinition = "DATE")
	Date dateOfBirth;

	@Column(name = "facebook_account_id", length = 100, unique = true)
	String facebookAccountId;

	@Column(name = "google_account_id", length = 100, unique = true)
	String googleAccountId;
	
	@Column(name = "is_active", columnDefinition = "TINYINT(1)")
	short isActive;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	Set<UserBook> userBooks;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	Set<Rating> ratings;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	Set<Comment> comments;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	Set<Follow> follows;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	Set<Order> orders;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	Set<SocialAccount> socialAccounts;
	
	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
	Token token;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id")
	Role role;
}
