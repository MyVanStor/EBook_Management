package com.example.EBook_Management_BE.common.entity;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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

	@Column(name = "facebook_account_id", length = 100, unique = true)
	String facebookAccountId;

	@Column(name = "google_account_id", length = 100, unique = true)
	String googleAccountId;
	
	@Column(name = "is_active", columnDefinition = "TINYINT(1)")
	short isActive;
	
	@OneToMany(mappedBy = "user")
	Set<UserBook> userBooks;
	
	@OneToMany(mappedBy = "user")
	Set<Rating> ratings;
	
	@OneToMany(mappedBy = "user")
	Set<Comment> comments;
	
	@OneToMany(mappedBy = "user")
	Set<Follow> follows;
	
	@OneToMany(mappedBy = "user")
	Set<Order> orders;
	
	@OneToMany(mappedBy = "user")
	Set<SocialAccount> socialAccounts;
	
	@OneToOne(mappedBy = "user")
	Token token;
	
	@OneToOne()
	@JoinColumn(name = "role_id")
	Role role;
}
